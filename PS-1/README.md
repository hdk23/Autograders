# CS 10 Problem Set 1 Autograder
## Henry Kim, CS 10 TA, Fall 2021

---

## Included In This Directory
* `AutogradePS1.java` - Java code that autogrades an assignment by running unit tests and integration tests
* `baker.jpg` - test image provided to students for detecting images
* `testlargestregion.png` - test image used to test the `largestRegion` method in `RegionFinder.java` 

## Methods Added for Autograding
### In `CamPaint.java`
```java
    // instance variables added for autograding
    private String recoloredFilePath = "Autograders/PS-1/recolored.png";
    private String paintingFilePath = "Autograders/PS-1/painting.png";
    private String webcamFilePath = "Autograders/PS-1/webcam.png";

    // getters/setters added for autograding
    public Color getTargetColor() {
        return targetColor;
    }
    
    public void setTargetColor (Color targetColor) {
        this.targetColor = targetColor;
    }
    
    public RegionFinder getRegionFinder() {
        return finder;
    }
    
    public BufferedImage getPainting() {
        return painting;
    }
    
    public Color getPaintColor() {
        return paintColor;
    }
    
    public void setRecoloredFilePath(String recoloredFilePath) {
        this.recoloredFilePath = recoloredFilePath;
    }
    
    public void setPaintingFilePath(String paintingFilePath) {
        this.paintingFilePath = paintingFilePath;
    }
    
    public void setWebcamFilePath(String webcamFilePath) {
        this.webcamFilePath = webcamFilePath;
    }
    
    // code added to `handlePress`
    else if (k == 'i') { // save the webcam image
        saveImage(image, webcamFilePath, "png");
    }
    else {
        System.out.println("unexpected key "+k);
    }
```

## Parts Requiring Manual Inspection
* **(5 points)**	Starting region growing at appropriate pixels
* **(5 points)**	Keeping track of visited pixels
* **(5 points)**	Keeping track of to-visit pixels
* **(5 points)** 	Visiting correctly colored neighbors

## Test Methods in `AutogradePS1.java`
### testColorMatch (5 points)
1. Randomly generate two identical colors
2. **(1 point)** Returns `true` if it receives two identical colors
3. **(1 point)** Returns `true` if it receives two similar colors that aren't identical
4. **(1 point)** Returns `false` if it receives two colors very different for each component
5. **(2 points)** Returns `false` if it receives two colors very different for one component

### testBigEnoughRegions (5 points)
1. Set the `RegionFinder finder`'s image to the file at `Autograders/PS-1/baker.jpg`
2. Find a color that produces enough regions by calling `findRandomColorRegions`
3. Iterate through each region found by `finder`
   1. Check whether each region is bigger than the minimum region size stored by the `RegionFinder`
   2. **(5 points, -1 point per incorrect region)** Each region is big enough
4. Report the number of regions identified as being too small

### testLargestRegion (10 points)
1. Set the `RegionFinder finder`'s image to the file at `Autograders/PS-1/testlargestregion.png`
2. `testlargestregion.png` is a Photoshop-generated image using three hues of green.
3. **(1 point)** Finds the largest region with dark green (should return `null`)
4. **(7 points)** Finds the largest region with Dartmouth green (should return a region)
5. **(1 point)** Returns that the largest region has a large enough size.
6. **(1 point)** Runs the `largestRegion` method 100 times and makes sure the largest region each time is larger than `minRegion` if not `null`

### testRecolorImage (10 points)
1. Initialize an `ArrayList` of `Integer`s to store color RGB values
2. Find a color that produces enough regions by calling `findRandomColorRegions`
3. Call `recolorImage`
4. **(4 points, -1 point per incorrect region)** Iterate over each of the regions and check whether each point in the region is of the same color
5. **(2 points)** Each region is set to a different color
6. **(2 points)** A different sequence of colors produced each time
7. **(2 points)** The original image remains untouched

### testDraw (5 points)
1. Set `CamPaint` to webcam mode
2. **(1 point)** Saves an image of the webcam 
3. Set `CamPaint` to painting mode
4. **(2 points)** Saves an image of the painting 
5. Set `CamPaint` to recolor mode
6. **(2 points)** Saves an image of the recolored image

### testHandleMousePress (5 points)
1. **(4 points)** `targetColor` set to the point's color on four random points
2. **(1 point)** Checks whether the image is `null`

### testProcessImage (10 points)
1. **(1 point)** Checks whether `targetColor` is `null`
2. **(1 point)** Checks whether `largestRegion` is `null` (change the `targetColor` if necessary)
3. Identify a `targetColor` that has a large enough region
4. **(2 points)** `regions` is updated
5. **(2 points)** The image is recolored
6. **(4 points)** The largest region is colored by the `paintColor` on the `painting`
