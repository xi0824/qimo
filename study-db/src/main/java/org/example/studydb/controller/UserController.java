package org.example.studydb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.studydb.model.dto.UserLoginDTO;
import org.example.studydb.model.dto.UserPasswordDTO;
import org.example.studydb.model.dto.UserProfileDTO;
import org.example.studydb.model.dto.UserRegisterDTO;
import org.example.studydb.model.vo.UserLoginVO;
import org.example.studydb.model.vo.UserVO;
import org.example.studydb.service.UserService;
import org.example.studydb.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 用户模块Controller
 * 处理用户登录、注册、个人信息管理等请求
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "用户模块", description = "用户登录、注册、个人信息管理接口")
public class UserController {

    @Resource
    private UserService userService;



    /**
     * 用户注册（无需登录，已放行）
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户填写用户名、密码等信息完成注册")
    public ResultUtil<UserVO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        UserVO userVO = userService.register(userRegisterDTO);
        // 匹配ResultUtil.success(T data, String msg)：数据在前，自定义消息在后
        return ResultUtil.success(userVO, "注册成功");
    }

    /**
     * 用户登录（无需登录，已放行）
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户名密码登录，返回JWT令牌和用户信息")
    public ResultUtil<UserLoginVO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        // 匹配ResultUtil.success(T data)：仅返回数据，使用默认消息"操作成功"
        return ResultUtil.success(userLoginVO);
    }

    /**
     * 查询用户信息（需要登录，从request中获取userId）
     */
    @GetMapping("/info")
    @Operation(summary = "查询用户信息", description = "查询当前登录用户的详细信息")
    public ResultUtil<UserVO> getUserInfo(HttpServletRequest request) {
        // 从request中获取JWT拦截器解析后的userId
        Long userId = (Long) request.getAttribute("loginUserId");

        // 非空校验，避免空指针，返回友好错误信息
        if (userId == null) {
            return ResultUtil.fail(401, "请先登录后再操作");
        }

        UserVO userVO = userService.getUserInfoById(userId);
        return ResultUtil.success(userVO);
    }

    /**
     * 修改个人信息（需要登录，从request中获取userId）
     */
    @PutMapping("/profile")
    @Operation(summary = "修改个人信息", description = "修改当前登录用户的昵称、头像、手机号、邮箱等信息")
    public ResultUtil<UserVO> updateProfile(HttpServletRequest request,
                                            @Valid @RequestBody UserProfileDTO userProfileDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");

        // 非空校验，避免空指针
        if (userId == null) {
            return ResultUtil.fail(401, "请先登录后再操作");
        }

        UserVO userVO = userService.updateProfile(userId, userProfileDTO);
        // 匹配ResultUtil.success(T data, String msg)：自定义修改成功消息
        return ResultUtil.success(userVO, "修改个人信息成功");
    }

    /**
     * 修改密码（需要登录，从request中获取userId）
     */
    @PutMapping("/password")
    @Operation(summary = "修改密码", description = "验证原密码后，修改为新密码")
    public ResultUtil<Boolean> updatePassword(HttpServletRequest request,
                                              @Valid @RequestBody UserPasswordDTO userPasswordDTO) {
        Long userId = (Long) request.getAttribute("loginUserId");

        // 非空校验，避免空指针
        if (userId == null) {
            return ResultUtil.fail(401, "请先登录后再操作");
        }

        Boolean success = userService.updatePassword(userId, userPasswordDTO);
        // 匹配ResultUtil.success(T data, String msg)：自定义修改密码成功消息
        return ResultUtil.success(success, "修改密码成功");
    }
}