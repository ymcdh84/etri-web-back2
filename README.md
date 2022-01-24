# ILJIN Framork Back-end (API App)  
## Pre Required
- Git  
- Java 11+  

## packages (com.iljin.apiServer)
### core
> 주요 서비스 및 메서드 등 백엔 핵심 로직 구현.    

#### aop
> Aspect 

#### config
> configurations

#### files
> file upload service

#### mail
> mail service

#### mPush
> mobile push service

#### schedule
> schedule job service

#### security
> application security settings

#### util
> key/value Pair, Util, Error, ...

### template
>for Basic APIs  
> like login, user 


## API Documentation
### Spring REST Docs
```src/docs/asciidoc/api-guide.adoc```  
: Asciidoc 문법으로 작성할 수 있습니다.   
: test, bootJar 실행시 해당 테스트에 의해 adoc 문서가 build/generated-snippets 에 생성됩니다.


 