package com.tdt4240.catchgame;

public class ScoreSinglePlayer {

    public CharacterSprite characterSprite;
    private int twolevelsup = 25;
    private int onelevelup = 15;
    private boolean levelUp = false;

    public ScoreSinglePlayer(CharacterSprite characterSprite) {

        this.characterSprite = characterSprite;

    }

    // Method for incrementing players score
    public void incrementScore(int scoreInc) {

        characterSprite.setScore(characterSprite.getScore() + scoreInc);
        System.out.println("Player score: " + characterSprite.getScore());

       /* //Check if "level up"
        if(!coreGame.getDifficulty().equals(coreGame.getHard())){
            //up two levels
            if (coreGame.getDifficulty().equals(coreGame.getMedium()) && characterSprite.getScore() >= twolevelsup){
                coreGame.setLevelUp();
            }
            //up one level
            if (characterSprite.getScore() >= onelevelup && levelUp == false){
                coreGame.setLevelUp();
                levelUp = true;
            }

        }*/

    }

    public void decrementScore(int scoreDec) {

        characterSprite.setScore(characterSprite.getScore() + scoreDec);

        //Check if "level gameover"
        // TODO: Find correct game-over variables and methods, need view
        if (characterSprite.getScore() < 0) {


        }

    }


   /* public void caughtObject(FallingObject object) {
        int objectPoints = object.getScore();
        String typeOfGame = this.coreGame.getGametype();

        if(object.getType().equals(this.coreGame.getGood())){
            incrementScore(objectPoints);
        }
        if (object.getType().equals(this.coreGame.getBad())){
            decrementScore(objectPoints);
        }
        if (object.getType().equals(this.coreGame.getPowerup())){
            // TODO: Implement power-up logic
            incrementScore(objectPoints);*/

            /*POWER-UP RULES
            Gets points for catching, in addition to logics for which is caught:

            SINGLE PLAYER:

            #1 Starbeetle: get 10 points
            #2 Ladybug: level down
            #3 Beetle (lightning): get one additional life

            Multi PLAYER:

            #1 Starbeetle: get 10 points
            #2 Ladybug: level down
            #3 Beetle (lightning): faster opponent

            */
      /*      if(typeOfGame.equals("single")) {
                if(objectPoints == 1) {
                    incrementScore(10);
                    this.coreGame.popup("Powerup! 10 extra points");
                }
                if(objectPoints == 2){
                    this.coreGame.setLevelDown();
                }
                if(objectPoints == 3){

                    // TODO: this does not work, fix that the lives increases
                    int currentLives = characterSprite.getLives();
                    int newLives = currentLives++;
                    characterSprite.setLives(newLives);
                    //this.coreGame.popup("Powerup! 1 extra life");
                }
            }*/

           /* if(typeOfGame.equals("multi")){
                if(objectPoints == 1) {
                    incrementScore(10);
                    // TODO: x2 points of the next caught items, don't know how yet
                }
                if(objectPoints == 2){
                    // TODO: increase size of own sprite and decrease opponent size
                    coreGame.characterSprite.maximizeCharacterSize();
                    //coreGame.opponent.minimizeCharacterSize();
                    // wait 5 seconds

                    coreGame.characterSprite.setCharacterSizeNormal();


                }
                if(objectPoints == 3){
                    // TODO: increase speed of opponent's falling objects
                }
            }*/

        }

/*    }

     if (this.type.equals(coreGame.getGood())) {
        if (characterSprite.getLives() == 1) {
            // TODO: Create game-over state, send to game-over state here
            System.out.println("Game over looooser");
        }
        characterSprite.setLives(characterSprite.getLives() - 1);
        System.out.println("Player has " + characterSprite.getLives() + " lives left");
    }*/


