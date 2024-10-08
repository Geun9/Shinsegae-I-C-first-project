package controller;

import common.ErrorCode;
import dto.request.AuthRequestDto;
import dto.request.UserRequestDto;
import dto.response.AuthResponseDto;
import handler.MemberInputHandler;
import common.ValidCheck;
import dto.request.AdminRequestDto;
import exception.Exception;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.naming.AuthenticationException;
import library.Script;
import service.AdminService;
import service.AuthService;
import service.UserService;
import service.serviceImpl.AdminServiceImpl;
import service.serviceImpl.AuthServiceImpl;
import service.serviceImpl.UserServiceImpl;

public class AuthController {


    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final ValidCheck validCheck = new ValidCheck();
    final Script script = new Script();
    final MemberInputHandler memberInputHandler = new MemberInputHandler();
    final AuthService authService = new AuthServiceImpl();
    final AdminService adminService = new AdminServiceImpl();
    final UserService userService = new UserServiceImpl();
    private boolean isQuit = false;

    /**
     * 메뉴 선택 1. 회원 가입 | 2. 로그인 | 3. 아이디 찾기 | 4. 비밀번호 재설정
     *
     * @param userType (1. 쇼핑몰, 2. 어드민)
     */
    public AuthResponseDto handleAuth(String userType) throws IOException {
        AuthResponseDto result = null;
        while (!isQuit) {
            try {
                script.selectLoginOrRegister();
                String menu = br.readLine().trim();
                validCheck.validateMenuNumber1To4(menu);

                switch (userType) {
                    case "1" -> {
                        switch (menu) {
                            case "1" -> registerUser();
                            case "2" -> result = loginUser();
                            case "3" -> findUserId();
                            case "4" -> resetUserPassword();
                        }
                    }
                    case "2" -> {
                        switch (menu) {
                            case "1" -> registerAdmin();
                            case "2" -> result = loginAdmin();
                            case "3" -> findAdminId();
                            case "4" -> resetAdminPassword();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 유저 회원 가입
     */
    private void registerUser() {
        try {

            UserRequestDto user = memberInputHandler.createUser();

            userService.createUser(user);
        } catch (IOException e) {
            System.out.println(ErrorCode.INVALID_VALUE.getMessage());
        }
    }

    /**
     * 유저 로그인
     */
    private AuthResponseDto loginUser() {

        boolean loginSuccessful = false;
        int maxAttempts = 3;
        int attempts = 0;
        AuthResponseDto auth = null;
        while (!loginSuccessful && attempts < maxAttempts) {
            try {
                script.login();
                AuthRequestDto user = memberInputHandler.login();
                auth = authService.loginUser(user);
                if (auth != null) {
                    loginSuccessful = true;
                    isQuit = !isQuit;
                    script.loginSuccess();
                }
            } catch (IOException e) {
                System.out.println(ErrorCode.INVALID_VALUE.getMessage());
                attempts++;
            } catch (AuthenticationException e) {
                System.out.println(e.getMessage());
                attempts++;
            }
        }

        if (!loginSuccessful) {
            System.out.println(ErrorCode.FAILURE_LOGIN.getMessage());
            script.loginFailure();
        }
        return auth;
    }

    /**
     * 쇼핑몰 사업자 회원 아이디 찾기
     */
    private void findUserId() throws IOException {
        script.getBizNo();
        String userId = userService.findUserId(memberInputHandler.getBusinessNumberInput());
        script.accountId(userId);
    }

    /**
     * 쇼핑몰 사업자 회원 비밀번호 재설정
     */
    private void resetUserPassword() throws IOException {
        UserController userController = new UserController();
        userController.editPwd();
    }


    /**
     * 어드민 회원 가입
     */
    private void registerAdmin() {
        try {

            AdminRequestDto admin = memberInputHandler.createAdmin();

            adminService.createAdmin(admin);
        } catch (IOException e) {
            System.out.println(ErrorCode.INVALID_VALUE.getMessage());
        }
    }

    /**
     * 직원 로그인
     */
    private AuthResponseDto loginAdmin() {
        boolean loginSuccessful = false;
        int maxAttempts = 3;
        int attempts = 0;
        AuthResponseDto auth = null;

        while (!loginSuccessful && attempts < maxAttempts) {
            try {
                script.login();
                AuthRequestDto user = memberInputHandler.login();
                auth = authService.loginAdmin(user);
                loginSuccessful = true;
                isQuit = !isQuit;
                script.loginSuccess();
            } catch (IOException e) {
                System.out.println(ErrorCode.INVALID_VALUE.getMessage());
                attempts++;
            } catch (AuthenticationException e) {
                System.out.println(e.getMessage());
                attempts++;
            }
        }

        if (!loginSuccessful) {
            System.out.println(ErrorCode.FAILURE_LOGIN.getMessage());
            script.loginFailure();
        }
        return auth;
    }

    /**
     * 어드민 아이디 찾기
     */
    private void findAdminId() throws IOException {
        String adminId = adminService.findAdminId(memberInputHandler.getAdminIdInput());
        script.accountId(adminId);
    }

    /**
     * 어드민 비밀번호 재설정
     */
    private void resetAdminPassword() throws IOException {
        AdminController adminController = new AdminController();
        adminController.editPwd();
    }


}