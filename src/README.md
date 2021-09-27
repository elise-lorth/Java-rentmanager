**** Projet Java Rentmanager ****
*** LORTHIOIS Elise ***

*** Commandes pour lancer le programme ***
Ouvrez l’invite de commande et déplacez-vous dans le dossier rentmanager. 
Exécutez la commande « mvn clean install tomcat7:run ». 
Enfin ouvrez une page internet et rendez-vous sur http://localhost:8080/rentmanager.

*** Difficultés rencontrées et leurs solutions ***
Le code a été fait petit à petit, et les vérifications ont d’abord été placées directement dans les fonctions create, avant d’être déplacées dans des fonctions comme isOK et isMail. Ce déplacement a permis d’abord de pouvoir appeler les fonctions dans les create et l’edit, mais aussi de respecter les conventions d’un beau code Java en évitant les fonctions trop longues et les duplicatas.
Lors de la création des pages d’affichages de détails, de nombreux problèmes sont apparu. Tout d’abord il a fallu pouvoir récupérer la classe entière d’un client ou d’une voiture à partir des identifiants. Cela a été possible à l’aide d’une boucle if ajoutant dans une liste chaque classe retrouvée tout d’abord à l’aide des fonctions clientIdByVehicle et vehicleIdByClient. Ensuite il a fallu créer plusieurs nouvelles fonctions pour ne pas afficher des données en double et avoir les bons comptes. En effet en utilisant les listes des findById et la fonction .size, des clients ou des voitures pouvaient être affichées en double. Les fonctions ont été créées dans ReservationDao et appelées dans ReservationService pour ensuite être utilisées dans les servlets de détails. Ces fonctions utilisent des nouvelles requêtes SQL et permettent d’avoir des détails complets.
La vérification des 30 jours a été plutôt difficile à mettre en place mais une coopération a permis d’en obtenir une fonctionnelle. Une fonction de tri a été créée dans ReservationDao, et une fois appelée dans le service elle permet de trier les réservations et donc de rendre la vérification fonctionnelle.
J’ai rencontré une difficulté pour les mocks. J’ai réalisé beaucoup de tests mais il y en a sur lesquels j’ai échoué. J’ai pu tester toutes les fonctions autre que create et edit dans les trois services, et autre que les fonctions de vérifications (à part isMail qui est un test unitaire). Je n’ai réalisé que quatre tests pour les fonctions des Dao, ceux de findById et findAll de clientDao. (Si vous pouviez me montrer comment j’aurais pu tester la vérification des isOK et les create et edit liés ce serait super !)

*** Choix effectués ***
Il y a un grand nombre de catch d’exception et d’erreurs, les catch ServicesExceptions et des Dao exception sont affichés dans la console, mais les erreurs faites par les utilisateurs leurs sont communiquées directement sur l’interface à l’aide de span et de request.setAttribute. 
Lors de la configuration des .jsp des pages création et modification, j’ai souhaité afficher des listes. Si elles ont été simple à mettre en place pour les clients et les voitures, j’ai eu du mal avec les réservations. Je voulais que la liste s’ouvre avec comme valeur par défaut le client ou la voiture déjà sélectionnée. La solution trouvée à été de rajouter comme option par défaut « Id de la voiture/du client actuel ». Ainsi, même si ce n’est pas le plus joli, les valeurs des véhicules et client de chaque modification sont affichées et sont comprises par l’interpréteur.




