#Servlet pour l'application JigglyPuff Messenger (Paul Faure et Elies Tali)

Bienvenue dans le repo contenant le projet servlet pour JigglyPuff Messenger. Pour télécharger le manuel, l'application et les autres fichiers utiles, rendez-vous sur https://etud.insa-toulouse.fr/~tali/DownloadPageJigglyMessenger/.
    

#Cloner ce repo et build le servlet

Comme pour le projet principal, le servlet est empaqueté dans un projet Gradle. Tout d'abord, clonez le repo et changez de reportoire avec

```bash
git clone https://git.etud.insa-toulouse.fr/pfaure/ServeurClavardage.git
cd ServeurClavardage
```

Ensuite, il vous faudra compiler le projet afin de créer un fichier war. La suite de commandes suivante vous permettra de compiler le projet, de vous déplacer dans le fichier de compilation et de rennomer le fichier

```bash
./gradlew build
cd build/libs
mv ./ServeurClavardage-1.0-SNAPSHOT.war ./ServletJiggly.war
```

Et voilà ! Vous pouvez maintenenant déployer le servlet dans un serveur Tomcat par exemple avec le fichier ServletJiggly.war.