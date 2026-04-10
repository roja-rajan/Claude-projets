@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup batch script
@REM ----------------------------------------------------------------------------
@echo off
set MAVEN_PROJECTBASEDIR=%~dp0

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

set DOWNLOAD_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar

FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties") DO (
    IF "%%A"=="wrapperUrl" SET WRAPPER_URL=%%B
)

if exist %WRAPPER_JAR% goto executeWrapper

echo Downloading Maven Wrapper...
powershell -Command "&{"^
    "$webclient = new-object System.Net.WebClient;"^
    "if (-not ([string]::IsNullOrEmpty('%WRAPPER_URL%'))) {"^
    "    $webclient.DownloadFile('%WRAPPER_URL%', '%WRAPPER_JAR%');"^
    "} else {"^
    "    $webclient.DownloadFile('%DOWNLOAD_URL%', '%WRAPPER_JAR%');"^
    "}"^
"}"

:executeWrapper
%JAVA_HOME%\bin\java.exe -classpath %WRAPPER_JAR% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %WRAPPER_LAUNCHER% %*
if "%ERRORLEVEL%"=="0" goto end
exit /b %ERRORLEVEL%
:end
