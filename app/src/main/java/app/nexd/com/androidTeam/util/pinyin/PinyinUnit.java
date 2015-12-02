/*
 * Copyright 2014 handsomezhou
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

package app.nexd.com.androidTeam.util.pinyin;

import java.util.ArrayList;
import java.util.List;


public class PinyinUnit implements Cloneable{
	//Whether Pinyin
	private boolean mPinyin;
	private int mStartPosition; //save starting index position that the variables in the original string. 
	/*
	 * save the string which single Chinese characters Pinyin(include Multiple Pinyin),or continuous non-kanji characters.
	 * if mPinyinBaseUnitIndex.size not more than 1, it means the is not Polyphonic characters.
	 */
	private List<PinyinBaseUnit> mPinyinBaseUnitIndex;

	public PinyinUnit() {
		mPinyin=false;
		mStartPosition=-1;
		mPinyinBaseUnitIndex=new ArrayList<PinyinBaseUnit>();
	}

	public boolean isPinyin() {
		return mPinyin;
	}

	public void setPinyin(boolean pinyin) {
		mPinyin = pinyin;
	}

	public int getStartPosition() {
		return mStartPosition;
	}

	public void setStartPosition(int startPosition) {
		mStartPosition = startPosition;
	}
	
	public List<PinyinBaseUnit> getPinyinBaseUnitIndex() {
		return mPinyinBaseUnitIndex;
	}

	public void setStringIndex(List<PinyinBaseUnit> stringIndex) {
		mPinyinBaseUnitIndex = stringIndex;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		PinyinUnit obj=(PinyinUnit)super.clone();
		
		obj.mPinyinBaseUnitIndex=new ArrayList<PinyinBaseUnit>();
		for(PinyinBaseUnit pbu:mPinyinBaseUnitIndex){
			obj.mPinyinBaseUnitIndex.add((PinyinBaseUnit)pbu.clone());
		}
		
		return obj;
	}
	
}
