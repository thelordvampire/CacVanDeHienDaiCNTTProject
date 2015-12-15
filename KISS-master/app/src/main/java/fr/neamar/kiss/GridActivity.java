package fr.neamar.kiss;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.neamar.kiss.database.Connection1;
import fr.neamar.kiss.grid_view.DynamicGridView;
import fr.neamar.kiss.grid_view_adapter.CheeseDynamicAdapter;
import fr.neamar.kiss.model.App;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import java.io.*;

public class GridActivity extends Activity {

    private static final String TAG = GridActivity.class.getName();

    private DynamicGridView gridView;

    private List<App> listApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        gridView = (DynamicGridView) findViewById(R.id.dynamic_grid);

        listApp = new ArrayList<App>();
        App app = null;
        List<PackageInfo> packageInfoList = getPackageManager().getInstalledPackages(0);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo PackInfo = packageInfoList.get(i);
            if ((PackInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = PackInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                list.add(appName);

                app = new App(appName, PackInfo.applicationInfo.loadIcon(getPackageManager()));
                listApp.add(app);
            }
        }

        for(int i = 0;i < 30; i++)
            listApp.add(new App());


        List<String> listApp1 = Connection1.getAllValue();
        List<App> listApp2 = new ArrayList<>();

        if(listApp1!=null && listApp1.size()>0) {
            for (String appName : listApp1) {
                if (!appName.equals("empty")) {
                    if (appName.equals("null")) {
                        listApp2.add(new App());
                    } else {
                        for (App appItem : listApp) {
                            if (appItem.getName().equals(appName)) {
                                listApp2.add(appItem);
                                break;
                            }
                        }
                    }
                }
            }
        }
        else
        {
            listApp2 = listApp;
        }

        gridView.setAdapter(new CheeseDynamicAdapter(this, listApp2, getResources().getInteger(R.integer.column_count)));
//        add callback to stop edit mode if needed
//        gridView.setOnDropListener(new DynamicGridView.OnDropListener()
//        {
//            @Override
//            public void onActionDrop()
//            {
//                gridView.stopEditMode();
//            }
//        });

        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "drag started at position " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                Log.d(TAG, String.format("drag item position changed from %d to %d", oldPosition, newPosition));
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridActivity.this, parent.getAdapter().getItem(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
        } else {
//            super.onBackPressed();


            try {
//                File a = new File("C:/Users/Bao/Desktop/launchers/KISS-master/app/src/main/java/fr/neamar/kiss/abc.xml");
                saveState();
                Log.d("xml", "co");
            }catch(Exception e)
            {
                Log.d("xml", "ko co");

            }

            Intent intent = new Intent(GridActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void saveState()
    {
        try {

            Connection1.deleteValue();
            Connection1.insertValue("empty");
            ListAdapter adapter = gridView.getAdapter();
            for(int i = 0; i< adapter.getCount(); i++) {
                App item = (App) adapter.getItem(i);
                if(item.getIcon()!=null) {
                    Connection1.insertValue(item.getName());
                }
                else
                {
                    Connection1.insertValue("null");
                }
            }

//            FileOutputStream fos = openFileOutput("data", Context.MODE_PRIVATE);
//            fos.write("empty".getBytes());
//
//
//            ListAdapter adapter = gridView.getAdapter();
//            for(int i = 0; i< adapter.getCount(); i++) {
//                App item = (App) adapter.getItem(i);
//                fos.write(item.getName().getBytes());
//            }
//            fos.close();
//
//            FileInputStream fis = openFileInput("data");
//            fis.re


//            Node apps = doc.getElementsByTagName("apps").item(0);
//
//            for(int i=0;i<apps.getChildNodes().getLength();i++)
//            {
//                Node appNode = apps.getChildNodes().item(i);
//                if(!appNode.getNodeValue().equals("empty"))
//                {
//                    apps.removeChild(appNode);
//                }
//            }
//
//            ListAdapter adapter = gridView.getAdapter();
//
//            for(int i = 0; i< adapter.getCount(); i++)
//            {
//                App item = (App) adapter.getItem(i);
//                Element app = doc.createElement("app");
//                app.setNodeValue(item.getName());
//                apps.appendChild(app);
//            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }
}
