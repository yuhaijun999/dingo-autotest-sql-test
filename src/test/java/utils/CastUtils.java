/*
 * Copyright 2021 DataCanvas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CastUtils {
    public static List construct1DListExcludeBlank(String constructStr, String splitCha){
        List constructList = new ArrayList();
        String[] originArray = constructStr.split(splitCha);
        constructList = Arrays.asList(originArray);

        return constructList;
    }

    public static List construct1DListIncludeBlank(String constructStr, String splitCha){
        List constructList = new ArrayList();
        String[] originArray = constructStr.split(splitCha,-1);
        constructList = Arrays.asList(originArray);

        return constructList;
    }

    public static List construct1DListIncludeBlankChangeable(String constructStr, String splitCha){
        List constructList = new ArrayList();
        String[] originArray = constructStr.split(splitCha,-1);
        for (int i = 0; i< originArray.length; i++) {
            constructList.add(originArray[i]);
        }
        return constructList;
    }

    public static List<List> construct2DListExcludeBlank(String constructStr, String listSplit, String strSplit){
        List<List> constructList = new ArrayList<List>();
        String[] originArray = constructStr.split(listSplit);

        for(int i=0; i < originArray.length; i++) {
            constructList.add(new ArrayList(Arrays.asList(originArray[i].split(strSplit))));
        }

        return constructList;
    }

    public static List<List> construct2DListIncludeBlank(String constructStr, String listSplit, String strSplit){
        List<List> constructList = new ArrayList<List>();
        String[] originArray = constructStr.split(listSplit,-1);

        for(int i=0; i < originArray.length; i++) {
            constructList.add(new ArrayList(Arrays.asList(originArray[i].split(strSplit,-1))));
        }

        return constructList;
    }

    public static Map kvListToMap(ArrayList keyList, ArrayList valueList) {
        Map castmap = new LinkedHashMap();
        for (int i = 0; i < keyList.size(); i ++) {
            castmap.put(keyList.get(i), valueList.get(i));
        }
        return castmap;
    }

}
