package poly.edu.vn.asm.settingscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import poly.edu.vn.asm.R;
import poly.edu.vn.asm.settingscreen.Setting;

public class AdapterSettings extends RecyclerView.Adapter<AdapterSettings.SettingsViewHoder> {

    Context context;
    ArrayList<Setting> listSettings;

    public AdapterSettings(Context context, ArrayList<Setting> listSettings) {
        this.context = context;
        this.listSettings = listSettings;
    }

    @NonNull
    @Override
    public SettingsViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_settings, null);
        return new SettingsViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHoder holder, int position) {
        Setting setting = listSettings.get(position);
        if(setting == null){
            return;
        }
        holder.nameSettings.setText(setting.getNameSetting());
        holder.iconSettings.setImageResource(setting.getIconSetting());

    }

    @Override
    public int getItemCount() {
        return listSettings.size();
    }

    public static class SettingsViewHoder extends RecyclerView.ViewHolder {
        TextView nameSettings;
        ImageView iconSettings;

        public SettingsViewHoder(@NonNull View itemView) {
            super(itemView);
            nameSettings = itemView.findViewById(R.id.tvSettings);
            iconSettings = itemView.findViewById(R.id.imgSettings);
        }
    }
}
