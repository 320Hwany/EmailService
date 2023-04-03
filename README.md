## 해결하고 싶은 문제

회원가입, 비밀번호 찾기 등의 기능이 있을 때 '회원을 어떻게 인증할 것인가'하는 문제가 있었습니다.  
이를 해결하기위한 방법으로 회원 이메일로 인증번호를 전송하면 회원이 받은 인증번호를 입력한 후  
일치하는지 확인한 후 기능을 제공하는 방법으로 해결하려고 했습니다.   

## 해결과정

스프링부트 3.0.5 버전을 사용했습니다.   
build.gradle에 다음과 같은 설정정보를 추가합니다.    

```
implementation 'org.springframework.boot:spring-boot-starter-mail'
```

application.yml에 다음과 같은 정보를 작성합니다. gmail을 사용했습니다.
```
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: {구글 이메일 주소}
    password: {발급받은 앱 비밀번호}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```
앱 비밀번호는 구글 계정관리 - 보안 - 2단계 인증에서 발급받을 수 있습니다.  

### EmailController

```
@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail(@RequestParam("email") String toEmail) {
        emailService.sendEmail(toEmail);
        return ResponseEntity.ok().build();
    }
}
```

### EmailService

```
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private final String subject = "회원을 인증해주세요";

    public void sendEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(makeUUID());
        mailSender.send(message);
    }

    private String makeUUID() {
        return UUID.randomUUID().toString().substring(0,8);
    }
}
```

## 결과

다음과 같이 8자리 인증번호가 전송되었습니다.    
이제 사용자가 인증번호를 입력하여 일치하는지 확인하여 회원가입, 비밀번호 찾기 등의 기능이 있을 때 '회원을 어떻게 인증할 것인가'하는 문제를 해결했습니다!   

<img width="343" alt="image" src="https://user-images.githubusercontent.com/84896838/229406961-eebb535f-a7b5-4f3e-941e-864a982bdf33.png">
