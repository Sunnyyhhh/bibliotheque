@echo off

REM === Compilation du projet Spring avec Maven ===
echo Compilation du projet Spring Boot...
cd C:\Users\miari\Documents\ProgSys\Springboot\bibliotheque\demo\demo
mvn clean package

IF ERRORLEVEL 1 (
    echo ❌ La compilation Maven a échoué. Arrêt du script.
    pause
    exit /b 1
)

REM === Variables pour le WAR et Tomcat ===
set WAR_PATH=C:\Users\miari\Documents\ProgSys\Springboot\bibliotheque\demo\demo\target\demo-0.0.1-SNAPSHOT.war
set DESTINATION=C:\apache-tomcat-10.1.28\webapps\demo.war

echo ⛔ Arrêt de Tomcat...
call "C:\apache-tomcat-10.1.28\bin\shutdown.bat"
timeout /t 3 >nul

echo 📁 Copie du fichier WAR...
del /f /q "%DESTINATION%" >nul 2>&1
copy /Y "%WAR_PATH%" "%DESTINATION%"

IF ERRORLEVEL 1 (
    echo ❌ La copie a échoué.
    pause
    exit /b 1
)

echo 🚀 Démarrage de Tomcat...
call "C:\apache-tomcat-10.1.28\bin\startup.bat"

echo ✅ Déploiement terminé avec succès !
pause
