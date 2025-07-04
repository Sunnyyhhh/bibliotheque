@echo off

REM Variables
set WAR_PATH=C:\Users\miari\Documents\ProgSys\Springboot\spring8_Bibliotheque\demo\demo\target\demo-0.0.1-SNAPSHOT.war
set DESTINATION=C:\apache-tomcat-10.1.28\webapps\demo.war

cd C:\Users\miari\Documents\ProgSys\Springboot\spring8_Bibliotheque\demo\demo
call mvn clean package

REM echo Arrêt de Tomcat...
REM  call "C:\apache-tomcat-10.1.28\bin\shutdown.bat"
REM  timeout /t 3 >nul

echo Copie du fichier WAR...
del /f /q "%DESTINATION%" >nul 2>&1
copy /Y "%WAR_PATH%" "%DESTINATION%"

IF ERRORLEVEL 1 (
    echo  La copie a échoué.
    pause
    exit /b 1
)

REM  echo  Démarrage de Tomcat...
REM  call "C:\apache-tomcat-10.1.28\bin\startup.bat"

echo Déploiement terminé.
pause
