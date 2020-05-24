package com.jempiere.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author jempiere
 * @version 1.3.0
 */
public class Formatter {
    private String lastCall = "";
    private String lastResult = "";
    private ArrayList<Fermata> formats = new ArrayList<>();

    /**
     * @since 1.0.0
     * <br>
     * default constructor
     */
    public Formatter() {
    }

    /**
     * @param formatter Another tool object created by this one.
     *                  <br>
     *                  This constructor is available but not recommended.
     * @since 1.1.0
     */
    public Formatter(Formatter formatter) {
        this.lastCall = formatter.getLast(true);
        this.lastResult = formatter.getLast(false);
        this.formats = new ArrayList<Fermata>(formatter.getFormats(true));
    }

    /**
     * @param data   a String to be formatted
     * @param format a set of characters that tells the formatter how data should be formatted.
     *               <br><br>
     *               Possible Formats:
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
     */
    public String format(String data, String format) {
        lastCall = data;
        String format1 = "ew(";//End with: (_)
        String format2 = "rw(";//Replace with   (_:_)
        String format3 = "bw(";//Begin with: (_)
        String format4 = "sc()";//Set Caps ()
        String format5 = "sl()";//Set lowercase ()
        String format6 = "sb(";//Separate by (#:'')
        String[] formats = {format1, format2, format3, format4, format5, format6};
        boolean formattable = false;
        for (String formatter : formats) {
            if (format.contains(formatter)) {
                formattable = true;
                break;
            }
        }
        if (!formattable)
            return data;
        if (format.contains(format1)) {
            String action = extrapolate(format, format1);
            data += action;
        }
        if (format.contains(format2)) {
            String action1 = extrapolate(format, format2);
            assert action1 != null : "unable to process";
            String toReplace = action1.substring(0, action1.indexOf(":"));
            String replaceWith = action1.substring(action1.indexOf(":") + 1);//
            data = data.replaceAll(toReplace, replaceWith);
        }
        if (format.contains(format3)) {
            String inter = extrapolate(format, format3);
            assert inter != null : "unable to process";
            data = inter.concat(data);
        }
        if (format.contains(format4)) {
            data = data.toUpperCase();
        }
        if (format.contains(format5)) {
            data = data.toLowerCase();
        }
        if (format.contains(format6)) {
            String action = extrapolate(format, format6);
            int everyDex = Integer.parseInt(action.substring(0, action.indexOf(":")));
            String putMe = action.substring(action.indexOf(":") + 1);
            StringBuilder builder = new StringBuilder();
            int counter = 1;
            for (char c : data.toCharArray()) {
                if (counter == everyDex) {
                    builder.append(c).append(putMe);
                    counter = 0;
                } else {
                    builder.append(c);
                }
                counter++;
            }
            data = builder.toString();
        }
        lastResult = data;
        return data;
    }

    /**
     * @param data   a String to be formatted
     * @param format a named set of characters that tells the formatter how data should be formatted.
     *               <br><br>
     *               Note: the code runs significantly faster if the format variable omits any unnecessary whitespace (i.e, inbetween formats)
     * @return a formatted String
     * @since 1.0.0
     */
    public String format(String data, Fermata format) {
        return format(data, format.getFormat());
    }

    /**
     * @param data   a String to be formatted
     * @param format a set of characters that tells the formatter how data should be formatted, saved under the name variable in a Fermata object.
     * @param name   a name for the format, which will be saved.
     *               <br><br>
     *               Note: the code runs significantly faster if the format variable omits any unnecessary whitespace (i.e, inbetween formats)
     * @return a formatted String
     * @since 1.0.0
     */
    public String format(String data, String format, String name) {
        addFormat(name, format);
        return format(data, format);
    }

    /**
     * @param data   a String to be formatted
     * @param format a named set of characters that tells the formatter how data should be formatted.
     * @param add    if this is true, the format will be saved.
     *               <br><br>
     *               Note: the code runs significantly faster if the format variable omits any unnecessary whitespace (i.e, inbetween formats)
     * @return a formatted String
     * @since 1.0.0
     */
    public String format(String data, Fermata format, boolean add) {
        if (add)
            addFormat(format);
        return format(data, format);
    }

    /**
     * @param call a boolean parameter, that is true to access the last call or false to access the last result.
     * @return the last Call or Result of the format() method
     * @since 1.0.0
     */
    public String getLast(boolean call) {
        if (call)
            return lastCall;
        return lastResult;
    }

    /**
     * @return every format inside of the ArrayList Formats
     * @since 1.0.0
     */
    public ArrayList getFormats() {
        String[] formats = new String[this.formats.size()];
        int i = 0;
        for (Fermata f : this.formats) {
            formats[i] = f.toString();
            i++;
        }

        return new ArrayList<>(Arrays.asList(formats));
    }

    /**
     * @param asFermata if true, returns the formats as Fermata objects.
     * @return every Fermata inside of the ArrayList Formats
     * @since 1.0.0
     */
    public ArrayList getFormats(boolean asFermata) {
        if (asFermata)
            return formats;
        return getFormats();
    }

    /**
     * @param name   the desired String name for a saved format
     * @param format the format to be saved
     * @since 1.0.0
     */
    public void addFormat(String name, String format) {
        for (Fermata f : formats) {
            if (f.equals(new Fermata(name, format))) {
                return;
            }
        }
        formats.add(new Fermata(name, format));
    }

    /**
     * @param format the named format to be saved
     * @since 1.0.0
     */
    public void addFormat(Fermata format) {
        formats.add(format);
    }

    /**
     * @param name the name of a previously saved format
     * @return a String[] of all of the formats that apply to the given name
     * @since 1.0.0
     */
    public String[] getFormats(String name) {

        ArrayList<String> results = new ArrayList<>();
        for (Fermata f : formats) {
            if (f.getName().equals(name))
                results.add(f.getFormat());
        }
        return (String[]) results.toArray();
    }

    //Extrapolate the function on the left from the one on the right
    private String extrapolate(String source, String function) {
        StringBuilder content = new StringBuilder();
        int functStart = source.indexOf(function) + function.length();
        source = source.substring(functStart);
        for (char c : source.toCharArray()) {
            if (c != ')') {
                content.append(c);
            } else {
                return content.toString();
            }
        }
        //System.out.println("Content created: "+content.toString());
        return source;
    }

    /**
     * @author jempiere
     * @version 1.0.0
     */
    public static class Fermata {
        private final String name;
        private final String format;

        /**
         * @param name   a final name for any given format
         * @param format a format to be saved and named
         * @since 1.0.0
         */
        public Fermata(final String name, final String format) {
            this.name = name;
            this.format = format;
        }

        /**
         * @return a string formatted as name:format of the current Fermata object
         * @since 1.0.0
         */
        public String toString() {
            return name + ":" + format;
        }

        /**
         * @return the current Fermata's format
         * @since 1.0.0
         */
        public String getFormat() {
            return format;
        }

        /**
         * @return the current Fermata's name
         * @since 1.0.0
         */
        public String getName() {
            return name;
        }

        /**
         * @param fermata another Fermata object
         * @return whether or not the parameter equals the current Fermata object in name and format
         * @since 1.0.0
         */
        public boolean equals(Fermata fermata) {
            return this.name.equals(fermata.name) && this.format.equals(fermata.format);
        }
    }


}
