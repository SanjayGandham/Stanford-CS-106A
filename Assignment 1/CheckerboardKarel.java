import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		while (frontIsClear()) {
			fillRow();
			faceNorth_nextrow();

		}
		if(frontIsBlocked()){
			turnLeft();
			fillRow();
		}

	}

	private void faceNorth_nextrow() {
		if (facingEast()) {
			turnLeft();
			if (frontIsClear()) {
				move();
				turnLeft();
				fillRow();
			}else{
				turnRight();
			}
		}
		if (facingWest()) {
			turnRight();
			if (frontIsClear()) {
				move();
				turnRight();
				fillRow();
			}

		}

	}

	private void fillRow() {
		while (frontIsClear()) {
			putBeeper();
			move();
			if (frontIsClear()) {
				move();
				if (frontIsBlocked()) {
					putBeeper();
					turnLeft();
					if(frontIsClear()){
						move();
						turnLeft();
						move();
						fillRow();
					}else{
						turnRight();
					}
				}
			} else {
				faceNorth_nextrow();
			}

		}

	}
}
