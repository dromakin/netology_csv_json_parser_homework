/*
 * File:     ParserException
 * Package:  org.dromakin
 * Project:  csv_json
 *
 * Created by dromakin as 18.01.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.01.18
 * copyright - Echelon Inc. 2023
 */

package org.dromakin;

public class ParserException extends Exception {

    public ParserException(String s) {
        super(s);
    }

    public ParserException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
