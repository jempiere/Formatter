package com.jempiere.util.home;
/**
 * @author jempiere
 * @version 1.0.0
 * */
public abstract class EFormatter {

    /**An array of pre-existing formats and their syntax: namely ew(, rw(, bw(, sc(), sl(), and sb()*/
    public String[] formats = {"ew(","rw(","bw(","sc()","sl()","sb("};
    /**Stores the last piece of unedited data, or "" if none have been created yet*/
    public String lastCall = "";
    /**Stores the last piece of edited data, or "" if none have been created yet*/
    public String lastResult = "";


    /**
     * @param data   a String to be formatted
     * @param format a set of characters that tells the formatter how data should be formatted.
     *               <br><br>
     *               Built-in Formats:
     *               <br>
     *               ew("something"): appends "something" to data
     *               <br>
     *               rw("this":"that"): replaces all of the instances of "this" in data with "that"
     *               <br>
     *               bw("anything"): prepends "anything" to the beginning of data
     *               <br>
     *               sc(): sets data to all caps
     *               <br>
     *               sl(): sets data to all lowercase
     *               <br>
     *               sb(someNumber:someSequence): at every given index someNumber, someSequence will be inserted in data
     *               <br><br>
     *               Note: the code runs significantly faster if the format variable omits any unnecessary whitespace (i.e, inbetween formats)
     * @return a formatted String
     * @since 1.0.0
     * */
    public String format(String data, String format){
        lastCall = data;
        if(format.contains(formats[0])){
            String param = extrapolate(formats[0],format);
            data += param;
        }
        if(format.contains(formats[1])){
            String param = extrapolate(formats[1],format);
            assert param != null : "unable to process";
            String toReplace = param.substring(0,param.indexOf(":"));
            String replaceWith = param.substring(param.indexOf(":")+1);
            data = data.replaceAll(toReplace,replaceWith);
        }
        if(format.contains(formats[2])){
            String param = extrapolate(formats[2],format);
            assert param != null : "unable to process";
            data = param.concat(data);
        }
        if(format.contains(formats[3])){
            data = data.toUpperCase();
        }
        if(format.contains(formats[4])){
            data = data.toLowerCase();
        }
        if(format.contains(formats[5])){
            //Formatting can be incredibly complicated or incredibly simple, pending on what you want.
            String param = extrapolate(formats[5],format);
            assert param != null : "unable to process";
            int constant = Integer.parseInt(param.substring(0,param.indexOf(":")));
            String insert = param.substring(param.indexOf(":"));
            StringBuilder builder = new StringBuilder();
            int counter = 1;
            for(char c : data.toCharArray()){
                if(counter == constant){
                    builder.append(c).append(insert);
                    counter = 0;
                } else {
                    builder.append(c);
                }
                counter++;
            }
            data = builder.toString();
        }
        String passGate = format(data,format,null);
        if(passGate != null){
            data = format(data,format,null);
        }
        lastResult = data;
        return data;
    }
    /**
     * @param data   a String to be formatted
     * @param format a set of characters that tells the formatter how data should be formatted.
     *               Draws from a list constructed by the user.
     * @param personal a null variable that differentiates the signature from format(String data, String format)
     * @return a formatted String
     * @since 1.0.0
     * */
    public abstract String format(String data, String format, Void personal);

    /**@param function  the specific function it is looking for in source, the list of functions
     * @param source    the String that holds all of the functions to be used on data
     *
     * @return the data inside of the requested function from the source
     * @since 1.0.0
     * */
    public String extrapolate(String source, String function){
        StringBuilder content = new StringBuilder();
        int functStart = source.indexOf(function) + function.length();
        source = source.substring(functStart);
        for(char c : source.toCharArray()){
            if(c != ')'){
                content.append(c);
            } else {
                return content.toString();
            }
        }
        return source;
    }

    /**
     * @return the value of lastCall
     * @since 1.0.0
     * */
    public String getLastCall() {
        return lastCall;
    }
    /**
     * @return the value of lastResult
     * @since 1.0.0
     * */
    public String getLastResult() {
        return lastResult;
    }
    /**
     * @return every format inside of the ArrayList Formats
     * @since 1.0.0
     * */
    public String[] getFormats() {
        return formats;
    }

}
