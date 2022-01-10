# CS 10 Problem Set 6 Autograder
## Henry Kim, CS 10 TA, Fall 2021

---

## Included In This Directory
* `AutogradePS6.java` - Java code that autogrades an assignment by running unit tests and integration tests
* `polyline.txt` - Point coordinates for hardcoding a polyline
* `postdelete2solution.png` - Solution image for testing delete mode
* `postmove2solution.png` - Solution image for testing move mode
* `postrecolor2solution.png` - Solution image for testing recolor mode
* `testdrawingshapessolution.png` - Solution image for testing drawing shapes
* `TestDrawingShapes.java` - Unit & integration testing for drawing shapes
* `TestEventHandlers.java` - Unit & integration testing for the event handler methods
* `TestPolyline.java` - Unit testing for the `Polyline` class
* `TestRectangle.java` - Unit testing for the `Rectangle` class
* `TestSketch.java` - Testing for the `Sketch` class
* `TestPS6.java` - Helper methods to compare images

## Methods Added for Autograding
### In `Editor.java`
```java

	public Shape getCurr() {
		return curr;
	}

	public int getMovingId() {
		return movingId;
	}

	public Point getDrawFrom() {
		return drawFrom;
	}

	public Point getMoveFrom() {
		return moveFrom;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

```

## Parts Requiring Manual Inspection
5	Sending strings between clients and server, using EditorCommunicator and ServerCommunicator

10	Composing messages, and decomposing and acting on them

10	Run loops in ServerCommunicator and EditorCommunicator (5 each)

5	Consistency of sketches across clients and server, including new clients

## Test Methods in `AutogradePS6.java` or Related Classes
### Rectangle Class (5 points)
* **(0.5 point)** Constructor that takes `x1`, `y1`, and `color`
* **(0.5 point)** Constructor that takes `x1`, `y1`, `x2`, `y2`, and `color`
* **(0.5 point)** `getColor` method
* **(0.5 point)** `setColor` method
* **(1 point)** `setCorners` and `contains` methods (also checks if (x1, y1) < (x2, y2))
* **(1 point)** `toString` method
* **(1 point)** `moveBy` and `contains` methods (move toward and away from a point)

### Polyline Class (5 points)
* **(0.5 point)** Constructor that takes `x1`, `y1`, and `color`
* **(0.5 point)** Constructor that takes `x1`, `y1`, `x2`, `y2`, and `color`
* **(0.5 point)** `getColor` method
* **(0.5 point)** `setColor` method
* **(1 point)** `setCorners` and `contains` methods (also checks if (x1, y1) < (x2, y2))
* **(1 point)** `toString` method
* **(1 point)** `moveBy` and `contains` methods (move toward and away from a point)

### Event Handlers (10 points)
#### Rectangle
* **(0.5 point)** creating a rectangle when handling press
* **(0.5 point)** handling drag for a rectangle correctly
* **(0.5 point)** setting current shape to null for a rectangle

#### Ellipse
* **(0.5 point)** creating an ellipse when handling press
* **(0.5 point)** handling drag for an ellipse correctly
* **(0.5 point)** setting current shape to null for an ellipse

#### Segment
* **(0.5 point)** creating a segment when handling press
* **(0.5 point)** handling drag for a segment correctly
* **(0.5 point)** setting current shape to null for a segment

#### Polyline
* **(0.5 point)** creating a polyline when handling press
* **(0.5 point)** handling drag for a polyline correctly
* **(0.5 point)** setting current shape to null for a polyline

#### No Shape
* **(0.5 point)** not moving a shape when the cursor isn't on a shape
* **(0.5 point)** not moving a shape when not clicked on
* **(0.5 point)** not recoloring a shape when not clicked on
* **(0.5 point)** not deleting a shape when not clicked on

#### Shape After Click
* **(0.5 point)** recoloring a shape when clicked on
* **(0.5 point)** setting moveFrom when handling drag for move mode
* **(0.5 point)** moving a shape when clicked on
* **(0.5 point)** deleting a shape when clicked on

### `TestDrawingShapes`: Drawing Shapes and Partial Shapes (10 points)
% similarity with solution image

### Consistency of Sketches (5 points)——included in `TestDrawingShapes`
% similarity between two editors

### Sketch Class (10 points)
2.5 points per shape dragged in the correct order
