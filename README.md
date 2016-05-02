# BlocNotePerso
Un bloc note simple, créé pour me mettre au développement android, et pour avoir un bloc note plus simple que ceux du play store.


### Téléchargement des sources ###
- Développé avec Android Studio.
- Testé sous Android Lollipop 5.1.1 via émulateur, et sur téléphone. 
- Récupérez le projet, et ouvrez le sous Android Studio. Lancez-le, il doit s'installer et fonctionner tout seul. 

### Fonctionnement de l'application ###
- L'application va créer un répertoire à la racine du téléphone, pour stocker les fichiers textes.
- Pour créer un nouveau fichier, entrez un nom et appuyez sur le bouton "+".
- Il ne vous reste plus qu'à taper vos notes dans la zone de texte prévue.
- Cliquez sur le bouton "Save" en bas pour enregistrer.
- De retour à la l'écran principal, vous avez une liste de vos fichiers textes (de vos notes)
- Cliquez sur l'une d'entre elle pour l'éditer.
- Pour SUPPRIMER une note, restez appuyé sur elle, un menu contextuel apparait pour vous proposer la suppression. 
- Cliquez sur l'option pour supprimer, ou à coté pour annuler. 

### Réalisation ###
Application réalisée par Guillaume Vrilliaux, étudiant en master d'informatique en Bourgogne. 
Plus d'informations? Me contacter à: g.vrilliaux@gmail.com

### Problèmes rencontrés ###
- Sous la version Marshmallow (via émulateur, pas testé sur téléphone), les fonctions de manipulation de fichier (File f[] = folder.listFile()) ne fonctionne pas correctement.

### Amélioration possibles ###
- Controler les noms de fichier (pas de noms contenant des espaces, sauts de lignes, caractères spéciaux, etc.).
- Retirer le focus automatique du téléphone sur la zone de texte d'ajout d'une nouvelle note (clavier qui apparait dés le lancement, peut être déplaisant)