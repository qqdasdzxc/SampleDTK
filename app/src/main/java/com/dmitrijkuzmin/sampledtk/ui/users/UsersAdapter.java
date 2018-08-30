package com.dmitrijkuzmin.sampledtk.ui.users;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dmitrijkuzmin.sampledtk.R;
import com.dmitrijkuzmin.sampledtk.data.entity.User;
import com.dmitrijkuzmin.sampledtk.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.dmitrijkuzmin.sampledtk.utils.Utils.*;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>
        implements Filterable {

    private List<User> userList = new ArrayList<>();
    private List<User> filteredUserList = new ArrayList<>();
    private String query = "";
    private UsersAdapterListener listener;

    public UsersAdapter(UsersAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_view, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(filteredUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    public void setItems(List<User> users) {
        userList = users;
        filteredUserList = users;
        getFilter().filter(query);
    }

    public void clearItems() {
        userList.clear();
        filteredUserList.clear();
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private TextView nameTextView;
        private TextView emailTextView;
        private TextView phoneTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username);
            nameTextView = itemView.findViewById(R.id.name);
            emailTextView = itemView.findViewById(R.id.email);
            phoneTextView = itemView.findViewById(R.id.phone);

            itemView.setOnClickListener(view -> {
                listener.onUserSelected(filteredUserList.get(getAdapterPosition()));
            });
        }

        public void bind(User user) {
            usernameTextView.setText(user.getUsername());
            nameTextView.setText(String.format("(%s)", user.getName()));
            emailTextView.setText(user.getEmail());
            phoneTextView.setText(user.getPhone());
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                query = charSequence.toString();

                List<User> filteredUsers = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredUsers = userList;
                } else {
                    for (User user : userList) {
                        if (isStringContainPattern(user.getName(), query) || isStringContainPattern(user.getUsername(), query)) {
                            filteredUsers.add(user);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filteredUsers.size();
                results.values = filteredUsers;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface UsersAdapterListener {
        void onUserSelected(User user);
    }
}
