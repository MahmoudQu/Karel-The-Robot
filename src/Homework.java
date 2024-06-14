import stanford.karel.SuperKarel;
public class Homework extends SuperKarel {
    private int width = 0;
    private int height = 0;
    private int moves =0;
    private int totalBeepers=1000;
    public void run(){
        setBeepersInBag(1000);
        scanAndCleanMap();
        startDividing();
        System.out.println("moves: "+ moves);
        System.out.println("Beepers: " + totalBeepers);
        System.out.println("Width : " + width + "\nHeight : " + height);
    }
    private void scanAndCleanMap(){
        System.out.println("Scanning and cleaning started..");
        cleanRow();
        height++; // this is to calculate the world's dimension (y-axis) while cleaning the map
        while (leftIsClear()){
            turnLeft();
            moveAndCount();
            turnLeft();
            cleanRow();
            height++;
            if (rightIsClear()){
                turnRight();
                moveAndCount();
                turnRight();
                cleanRow();
                height++;
            } else
                turnAround();
        }
        backHome();
        System.out.println("Cleaning is done, and the robot is back home\n");
    }
    private void cleanRow(){
        checkAndPickBeeper();
        catchingBeepers();
    }
    private void startDividing(){
        //if(frontIsBlocked()){
        //   turnLeft();
        //}
        System.out.println("Dividing the map in progress");
        mapDivider(width,height);
    }
    private void backHome(){
        System.out.println("Back home in progress");
        while (notFacingWest())
            turnLeft();
        while (frontIsClear())
            moveAndCount();
        turnLeft();
        while (frontIsClear())
            moveAndCount();
        turnLeft();
    }
    private void mapDivider(int width, int height){
        if( (width==1 && height==1) || (width==1 && height==2) || (width==2 && height==1))
            oneRoomDividing();
        else if( (width==2 && height==2) || (width==1 && (height==3 || height==4)) ||
                (height==1 && (width==3 || width==4))){
            twoRoomDividing(width,height);
        }else if((width==1 && (height==5 || height==6)) ||
                height==1 && (width==5 || width==6) || (width==2 && height==3) || width==3 && height==2){
            threeRoomDividing(width,height);
        }else{
            if((width==2 && height==7) || (width==7 && height==2))
                fourChamDividingForNx2_2xN(width,height);
            else
                fourRoomDividing(width,height);
        }
    }
    private void oneRoomDividing(){
        System.out.println("Do nothing, keep the map as is");
        backHome();
    }
    private void twoRoomDividing(int width,int height){
        if(height==1 && width==3){
            moveAndCount();
            addBeeper();
        }else if(height==1 && width==4){
            edgeCaseDividing();
        }else if(width==1 && height==3){
            moveAndCount();
            addBeeper();
        }else if(width==1 && height==4){
            edgeCaseDividing();
        }else{
            addBeeper();
            moveAndCount();
            turnLeft();
            moveAndCount();
            addBeeper();
        }
        backHome();
    }
    private void edgeCaseDividing(){
        moveAndCount();
        addBeeper();
        moveAndCount();
        moveAndCount();
        addBeeper();
    }
    private void edgeCaseDividing2(){
        addBeeper();
        moveAndCount();
        moveAndCount();
        addBeeper();
        moveAndCount();
        moveAndCount();
        addBeeper();
    }
    private void threeRoomDividing(int width,int height){
        if(width==1 && height==5){
            turnLeft();
            edgeCaseDividing();
        }
        else if(width==1 && height==6){
            turnLeft();
            edgeCaseDividing2();
        }else if(height==1 && width==5){
            edgeCaseDividing();
        }else if(height==1 && width==6){
            edgeCaseDividing2();
        }else if(width==3 && height==2){
            addBeeper();
            moveAndCount();
            moveAndCount();
            addBeeper();
            turnLeft();
            moveAndCount();
            turnLeft();
            moveAndCount();
            addBeeper();
        }else{
            addBeeper();
            moveAndCount();
            turnLeft();
            moveAndCount();
            addBeeper();
            moveAndCount();
            turnLeft();
            moveAndCount();
            addBeeper();
        }
        backHome();
    }
    int countChambers=0;
    final int towMoves=2;
    private void fourRoomDividing(int width, int height){

        if(width%2==0 && height==1){
            fourChamDividingForNx1_1xN(width,height);
        }
        else if(width%2==1 && height==1){
            if(width==7){
                moveAndCount();
                addBeeper();
                for(int i=2; i<=width; i+=2){
                    if(i==6){
                        addBeeper();
                        break;
                    }else{
                        moveToPosition(towMoves);
                        addBeeper();
                    }
                }
            }else{
                addBeeper();
                moveAndCount();
                width--;
                fourChamDividingForNx1_1xN(width,height);
            }
        }
        else if(height%2==0 && width==1){
            turnLeft();
            fourChamDividingForNx1_1xN(width,height);
        }else if(height%2==1 && width==1){
            turnLeft();
            if(height==7){
                moveAndCount();
                addBeeper();
                for(int i=2; i<=height; i+=2){
                    if(i==6){
                        addBeeper();
                        break;
                    }else{
                        moveToPosition(towMoves);
                        addBeeper();
                    }
                }
            }else{
                addBeeper();
                moveAndCount();
                height--;
                fourChamDividingForNx1_1xN(width,height);
            }
        }else if(width==2 && height>7){
            fourChamDividingForNx2_2xN(width,height);
        }
        else if(height==2 && width>7){
            fourChamDividingForNx2_2xN(width,height);
        }
        else if(width%2==0 && height%2==0){
            evenWidthDivider(width);
            evenHeightDivider(height);
        }else if(width%2==0 && height%2==1){
            evenWidthDivider(width);
            oddHeightDivider(height);
        }else if(width%2==1 && height%2==1){
            oddWidthDivider(width);
            oddHeightDivider(height);
        }else{
            oddWidthDivider(width);
            evenHeightDivider(height);
        }
        backHome();
    }
    private void fourChamDividingForNx2_2xN(int width,int height){
        if(height==2){
            fourRoomDividing(width,height-1);
            turnLeft();moveAndCount(); turnRight();
            countChambers=0;
            fourRoomDividing(width,height-1);
        }
        else if(width==2){
            fourRoomDividing(width-1,height);
            moveAndCount();
            countChambers=0;
            fourRoomDividing(width-1,height);
        }
    }
    private void fourChamDividingForNx1_1xN(int width,int height){
        if(height==1 && width!=7){
            int chamberSize=(width/4)-1;
            moveToPosition(chamberSize);
            addBeeper();
            countChambers++;
            int start=chamberSize+1;
            for(int i=start; i<=width;){
                moveToPosition(start);
                addBeeper();
                countChambers++;
                if(countChambers==4){
                    addingBeepers();
                    break;
                }
            }
        }else if(width==1 && height!=7){
            int chamberSize=(height/4)-1;
            moveToPosition(chamberSize);
            addBeeper();
            countChambers++;
            int start=chamberSize+1;
            for(int i=start; i<=height;){
                moveToPosition(start);
                addBeeper();
                countChambers++;
                if(countChambers==4){
                    addingBeepers();
                    break;
                }
            }
        }
    }
    private void evenWidthDivider(int width){
        int dividingPosition = (width/2);
        moveToPosition(dividingPosition);
        addVerticalLine();
        turnLeft();
        moveAndCount();
        addVerticalLine();
    }
    private void evenHeightDivider(int height){
        int dividingPosition = (height/2);
        if(facingSouth()){
            turnAround();
            moveToPosition(dividingPosition-1);
            turnLeft();
            addingBeepers();
            turnRight();
            putRightLine();
            turnRight();
            putRightLine();
            turnLeft();
            backHome();
        }
        else{
            turnAround();
            moveToPosition(dividingPosition);
            turnRight();
            addingBeepers();
            turnRight();
            putRightLine();
            turnRight();
            putRightLine();
            backHome();
        }
    }
    private void oddWidthDivider(int width){
        int dividingPosition = (width/2);
        moveToPosition(dividingPosition);
        addVerticalLine();
    }
    private void oddHeightDivider(int height){
        int dividingPosition = (height/2);
        if(facingSouth()){
            turnAround();
            moveToPosition(dividingPosition);
            turnRight();
            addingBeepers();
            turnAround();
            addingBeepers();
            backHome();
        }
        else{
            turnAround();
            moveToPosition(dividingPosition);
            turnLeft();
            addingBeepers();
            turnAround();
            addingBeepers();
            backHome();
        }
    }
    private void moveToPosition(int position){
        for(int i=0;i<position;i++)
            moveAndCount();
    }
    private void addVerticalLine(){
        addBeeper();
        turnLeft();
        addingBeepers();
    }
    private void putRightLine(){
        moveAndCount();
        addBeeper();
        turnRight();
        addingBeepers();
    }
    private void rowDownLeft(){
        moveAndCount();
        turnLeft();
        moveAndCount();
    }
    private void rowDownRight(){
        moveAndCount();
        turnRight();
        moveAndCount();
    }
    private void moveAndCount(){
        move();
        moves++;
    }
    private void catchingBeepers() {
        int currentWidth=1;
        while(frontIsClear()){
            moveAndCount();
            currentWidth++;
            checkAndPickBeeper();
        }
        width=Math.max(width,currentWidth);
    }
    private void addingBeepers() {
        while(frontIsClear()){
            moveAndCount();
            addBeeper();
        }
    }
    private void checkAndPickBeeper() {
        if(beepersPresent()){
            pickBeeper();
            totalBeepers++;
        }
    }
    private void addBeeper(){
        if(noBeepersPresent()){
            putBeeper();
            totalBeepers--;
        }
    }
}