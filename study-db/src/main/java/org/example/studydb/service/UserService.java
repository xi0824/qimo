package org.example.studydb.service;

import org.example.studydb.model.dto.UserLoginDTO;
import org.example.studydb.model.dto.UserPasswordDTO;
import org.example.studydb.model.dto.UserProfileDTO;
import org.example.studydb.model.dto.UserRegisterDTO;
import org.example.studydb.model.vo.UserLoginVO;
import org.example.studydb.model.vo.UserVO;

/**
 * 用户模块Service接口
 */
public interface UserService {

    /**
     * 用户注册
     * @param userRegisterDTO 注册请求参数
     * @return 注册成功后的用户信息
     */
    UserVO register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userLoginDTO 登录请求参数
     * @return 登录结果（包含JWT令牌和用户信息）
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return 用户详细信息
     */
    UserVO getUserInfoById(Long userId);

    /**
     * 修改个人信息
     * @param userId 用户ID
     * @param userProfileDTO 个人信息修改参数
     * @return 修改后的用户信息
     */
    UserVO updateProfile(Long userId, UserProfileDTO userProfileDTO);

    /**
     * 修改密码
     * @param userId 用户ID
     * @param userPasswordDTO 密码修改参数
     * @return 是否修改成功（true：成功，false：失败）
     */
    Boolean updatePassword(Long userId, UserPasswordDTO userPasswordDTO);
}