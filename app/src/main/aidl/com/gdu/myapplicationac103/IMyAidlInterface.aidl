// IMyAidlInterface.aidl
package com.gdu.myapplicationac103;

// Declare any non-default types here with import statements
import com.gdu.myapplicationac103.DUser;

interface IMyAidlInterface {


String getIDS();

String getIDName(String name);

void  startActivity();

DUser getUser();

}
