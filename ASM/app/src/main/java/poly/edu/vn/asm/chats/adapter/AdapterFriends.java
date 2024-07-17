package poly.edu.vn.asm.chats.adapter;

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
import poly.edu.vn.asm.chats.Friends;

public class AdapterFriends extends RecyclerView.Adapter<AdapterFriends.FriendHoderView> {
    Context context;
    ArrayList<Friends> listFriends;

    public AdapterFriends(Context context, ArrayList<Friends> listFriends) {
        this.context = context;
        this.listFriends = listFriends;
    }

    @NonNull
    @Override
    public FriendHoderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_chats, null);
        return new FriendHoderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHoderView holder, int position) {
        Friends friends = listFriends.get(position);
        holder.imgAvatar.setImageResource(friends.getAvatar());
        holder.tvName.setText(friends.getName());
        holder.tvMessage.setText(friends.getMessage());


    }

    @Override
    public int getItemCount() {
        return listFriends.size();
    }

    public static class FriendHoderView extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvMessage;
        public FriendHoderView(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
