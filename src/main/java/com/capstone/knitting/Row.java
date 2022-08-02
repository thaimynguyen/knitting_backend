package com.capstone.knitting;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;

public class Row {

    public static void main (String[] args) {
        String input = "Row 1: * K1, P1, K5 *, P1";
        //(K1, P1, K5, P1)
        // Get an array of stitchBlocks from input (1 row only)
        ArrayList<StitchBlock> stitchBlocks = generateStitchBlocks(input);

        // Generate an array of stitches from stitchBlocks array
        ArrayList<Stitch> row = generateOneRowOfStitches(stitchBlocks);

        // convert array of objects to json
        String rowAsString = convertArrayToJson(row);
        System.out.println(rowAsString);
    }

    public static ArrayList<StitchBlock> generateStitchBlocks(String input) {
        // parse string input into an array of stitchBlock capture groups
        String stitchBlockPattern = "([KP])(\\d+)";  // K or P + count
        Pattern r = Pattern.compile(stitchBlockPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = r.matcher(input);
        ArrayList<StitchBlock> matches = new ArrayList<StitchBlock>();

        while (matcher.find()) {
            System.out.println(matcher.group(0) + " " + matcher.group(1) + " " + matcher.group(2));
            String stitchType = matcher.group(1);
            int count = Integer.parseInt(matcher.group(2));
            StitchBlock stitchBlock = new StitchBlock(stitchType, count);
            matches.add(stitchBlock);
        }


        return matches;
    }

    public static ArrayList<Stitch> generateOneRowOfStitches(ArrayList<StitchBlock> stitchBlocks){
        ArrayList<Stitch> row = new ArrayList<>();
        for (StitchBlock stitchBlock: stitchBlocks) {
            ArrayList<Stitch> stitches = stitchBlock.generateStitches();
            row.addAll(stitches);
        }
        return row;
    }

    public static String convertArrayToJson(ArrayList<Stitch> arrayList) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(arrayList);
        return jsonString;
    }
}
