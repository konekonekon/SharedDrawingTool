package MarvinRecognition;

import static marvin.MarvinPluginCollection.floodfillSegmentation;
import static marvin.MarvinPluginCollection.moravec;
import static marvin.MarvinPluginCollection.scale;

import java.awt.*;
import java.util.*;

import marvin.image.*;
import marvin.io.*;
import marvin.plugin.*;
import marvin.util.*;

public class ShapesExample {

    public ShapesExample(){
        // Scale down the image since the desired features can be extracted
        // in a lower resolution.
        MarvinImagePlugin scale = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.transform.scale");
        MarvinImage image = MarvinImageIO.loadImage("./res/shapes.png");
        scale(image.clone(), image, 269);

        // segment each object
        MarvinSegment[] objs = floodfillSegmentation(image);
        MarvinSegment seg;
        //System.out.println(objs.length);
        // to recognize color and delete white form

        // For each object...
        // Skip position 0 which is just the background
        for(int i=1; i<objs.length; i++){
            seg = objs[i];
            MarvinImage imgSeg = image.subimage(seg.x1-5, seg.y1-5, seg.width+10, seg.height+10);
            MarvinAttributes output = new MarvinAttributes();
            output = moravec(imgSeg, null, 18, 1000000);
            System.out.println("figure "+(i-1)+":" + getShapeName(getNumberOfCorners(output)));
        }
    }

    public String getShapeName(int corners){
        switch(corners){
            case 3: return "Triangle";
            case 4: return "Rectangle";
            case 5: return "Pentagon";
        }
        return null;
    }

    private static int getNumberOfCorners(MarvinAttributes attr){
        int[][] cornernessMap = (int[][]) attr.get("cornernessMap");
        int corners=0;
        ArrayList<Point> points = new ArrayList<Point>();
        for(int x=0; x<cornernessMap.length; x++){
            for(int y=0; y<cornernessMap[0].length; y++){
                // Is it a corner?
                if(cornernessMap[x][y] > 0){
                    // This part of the algorithm avoid inexistent corners
                    // detected almost in the same position due to noise.
                    Point newPoint = new Point(x,y);
                    if(points.size() == 0){
                        points.add(newPoint); corners++;
                    }else {
                        boolean valid=true;
                        for(Point p:points){
                            if(newPoint.distance(p) < 10){
                                valid=false;
                            }
                        }
                        if(valid){
                            points.add(newPoint); corners++;
                        }
                    }
                }
            }
        }
        return corners;
    }

    public static void main(String[] args) {
        new ShapesExample();
    }
}