/*
 * Copyright 2020 John Grosh <john.a.grosh@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.utils;

import java.util.regex.Pattern;

public class TimeUtil
{

    public static String formatTime(long duration)
    {
        if(duration == Long.MAX_VALUE)
            return "LIVE";
        long seconds = Math.round(duration/1000.0);
        long hours = seconds/(60*60);
        seconds %= 60*60;
        long minutes = seconds/60;
        seconds %= 60;
        return (hours>0 ? hours+":" : "") + (minutes<10 ? "0"+minutes : minutes) + ":" + (seconds<10 ? "0"+seconds : seconds);
    }

    /**
     * @param args formatted as: [+ | -] &lt;HH:MM:SS | MM:SS | SS&gt;
     * @return Time in milliseconds, negative if seeking backwards relatively
     */
    public static SeekTime parseTime(String args)
    {
        if (args.length() == 0) return null;

        boolean relative = false; // seek forward or backward
        boolean isSeekingBackwards = false;
        char first = args.charAt(0);
        if (first == '+' || first == '-')
        {
            relative = true;
            isSeekingBackwards = first == '-';
            args = args.substring(1);
        }

        long seconds;
        long minutes = 0;
        long hours = 0;
        if (Pattern.matches("^(\\d\\d):(\\d\\d):(\\d\\d)$", args))
        {
            hours = Integer.parseInt(args.substring(0, 2));
            minutes = Integer.parseInt(args.substring(3, 5));
            seconds = Integer.parseInt(args.substring(6));
        } else if (Pattern.matches("^(\\d\\d):(\\d\\d)$", args))
        {
            minutes = Integer.parseInt(args.substring(0, 2));
            seconds = Integer.parseInt(args.substring(3, 5));
        } else if (Pattern.matches("^(\\d\\d)$", args))
        {
            seconds = Integer.parseInt(args.substring(0, 2));
        } else return null;

        long milliseconds = hours * 3600000 + minutes * 60000 + seconds * 1000;
        if (relative && isSeekingBackwards) milliseconds = -milliseconds;

        return new SeekTime(milliseconds, relative);
    }


    public static class SeekTime
    {
        public final long milliseconds;
        public final boolean relative;

        public SeekTime(long milliseconds, boolean relative)
        {
            this.milliseconds = milliseconds;
            this.relative = relative;
        }
    }
}
