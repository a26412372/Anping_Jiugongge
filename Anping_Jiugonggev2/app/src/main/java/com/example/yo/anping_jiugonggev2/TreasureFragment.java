package com.example.yo.anping_jiugonggev2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FilenameFilter;

import static com.example.yo.anping_jiugonggev2.Level1Activity.image_path;

public class TreasureFragment extends Fragment {

    private GridView mGridView;
    private File[] files;
    public TreasureFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_treasure, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView = (GridView) getActivity().findViewById(R.id.gridView);
        mGridView.setNumColumns(3);
    }

    public void onStart() {
        super.onStart();
        files = getImagePath();
        if (files != null) {
            mGridView.setAdapter(new ImageAdapter(getActivity(),files));
        }

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("刪除圖片")
                        .setMessage("確定要刪除圖片?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                files[position].delete();
                                files = getImagePath();
                                mGridView.setAdapter(new ImageAdapter(getActivity(),files));
                            }
                        }).setNegativeButton("取消",null).show();

                return true;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showImageDialog(files[position].getPath());
            }
        });
    }

    private File[] getImagePath() {
        File folder = new File(image_path);
        FilenameFilter filenameFilter = new FilenameFilter() {
            String[] f = {"IMAGE_"};
            public boolean accept(File dir, String name) {
                for (String aF : f) {
                    if (name.contains(aF))
                        return true;
                }
                return false;
            }
        };
        return folder.listFiles(filenameFilter);
    }


    private void showImageDialog(String path) {
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.image_dialog, null);
        ImageView view = (ImageView) layout.findViewById(R.id.image);
        new AlertDialog.Builder(getActivity())
                .setView(layout)
                .show();
        view.setBackgroundResource(R.drawable.homepage_background);
        Glide.with(getActivity())
                .load(path)
                .into(view);
    }

    class ImageAdapter extends BaseAdapter {

        private Context mContext;
        private File[] mFiles;

        ImageAdapter(Context context, File[] files) {
            this.mContext = context;
            this.mFiles = files;
        }

        public int getCount() {
            return mFiles.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(280, 280));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }

            Glide
                    .with(mContext)
                    .load(files[position])
                    .thumbnail(0.1f)
                    .centerCrop()
                    .fitCenter()
                    .into(imageView);

            return imageView;
        }
    }
}
