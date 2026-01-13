package org.example.studydb.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.studydb.exception.BusinessException;
import org.example.studydb.exception.ErrorCode;
import org.example.studydb.mapper.UserMapper;
import org.example.studydb.model.dto.UserLoginDTO;
import org.example.studydb.model.dto.UserPasswordDTO;
import org.example.studydb.model.dto.UserProfileDTO;
import org.example.studydb.model.dto.UserRegisterDTO;
import org.example.studydb.model.entity.User;
import org.example.studydb.model.vo.UserLoginVO;
import org.example.studydb.model.vo.UserVO;
import org.example.studydb.service.UserService;
import org.example.studydb.util.JwtUtil;
import org.example.studydb.util.PasswordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户模块Service实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @Override
    public UserVO register(UserRegisterDTO userRegisterDTO) {

        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String confirmPassword = userRegisterDTO.getConfirmPassword();

        System.out.println("：1" );

        if (!password.equals(confirmPassword)) {

            throw new BusinessException(ErrorCode.PARAM_ERROR, "两次输入密码不一致");
        }


        // ✅ 改为使用 LambdaQueryWrapper 查询用户名是否存在
        User existUser = userMapper.selectByUsername(username);

        if (existUser != null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户名已被占用");
        }



        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setPassword(PasswordUtil.encryptPassword(password));

        if (!StringUtils.hasText(user.getNickname())) {
            user.setNickname(username);
        }

        boolean saveSuccess = this.save(user);




        if (!saveSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，请稍后重试");
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 用户登录
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        // ✅ 改为使用 LambdaQueryWrapper 查询用户
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }

        boolean passwordMatch = PasswordUtil.verifyPassword(password, user.getPassword());
        if (!passwordMatch) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
        }

        String token = jwtUtil.generateToken(user.getUserId(), user.getUsername());

        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setToken(token);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userLoginVO.setUserInfo(userVO);

        return userLoginVO;
    }

    /**
     * 根据用户ID查询用户信息
     */
    @Override
    public UserVO getUserInfoById(Long userId) {
        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 修改个人信息
     */
    // 在 updateProfile 方法中修正类型转换
    @Override
    public UserVO updateProfile(Long userId, UserProfileDTO userProfileDTO) {
        User existUser = this.getById(userId);
        if (existUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }

        User user = new User();
        user.setUserId(userId); // 直接赋值Long类型
        user.setNickname(userProfileDTO.getNickname());
        user.setAvatar(userProfileDTO.getAvatar());
        user.setPhone(userProfileDTO.getPhone());
        user.setEmail(userProfileDTO.getEmail());

        boolean updateSuccess = this.updateById(user);
        if (!updateSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改个人信息失败，请稍后重试");
        }

        User updatedUser = this.getById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(updatedUser, userVO);
        return userVO;
    }


    /**
     * 修改密码
     */
    @Override
    public Boolean updatePassword(Long userId, UserPasswordDTO userPasswordDTO) {
        String oldPassword = userPasswordDTO.getOldPassword();
        String newPassword = userPasswordDTO.getNewPassword();
        String confirmNewPassword = userPasswordDTO.getConfirmNewPassword();

        if (!newPassword.equals(confirmNewPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "两次输入新密码不一致");
        }

        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }

        boolean oldPasswordMatch = PasswordUtil.verifyPassword(oldPassword, user.getPassword());
        if (!oldPasswordMatch) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "原密码错误");
        }

        user.setPassword(PasswordUtil.encryptPassword(newPassword));
        boolean updateSuccess = this.updateById(user);

        if (!updateSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改密码失败，请稍后重试");
        }

        return true;
    }
}