package my.edu.utar.foodcourtadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapterDrink extends FirebaseRecyclerAdapter<MainModelDrink,MainAdapterDrink.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterDrink(@NonNull FirebaseRecyclerOptions<MainModelDrink> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull MainModelDrink mainModelDrink) {
        holder.title.setText(mainModelDrink.getTitle());
        holder.description.setText(mainModelDrink.getDescription());

        Glide.with(holder.imagePath.getContext())
                .load(mainModelDrink.getImagePath())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imagePath);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.imagePath.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();
                View view=dialogPlus.getHolderView();

                EditText bestDrink=view.findViewById(R.id.txtBestDrink);
                EditText categoryId=view.findViewById(R.id.txtCategoryId);
                EditText description=view.findViewById(R.id.txtDescription);
                EditText imagePath=view.findViewById(R.id.txtImagePath);
                EditText locationId=view.findViewById(R.id.txtLocationId);
                EditText price=view.findViewById(R.id.txtPrice);
                EditText priceId=view.findViewById(R.id.txtPriceId);
                EditText star=view.findViewById(R.id.txtStar);
                EditText timeId=view.findViewById(R.id.txtTimeId);
                EditText timeValue=view.findViewById(R.id.txtTimeValue);
                EditText title=view.findViewById(R.id.txtTitle);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                bestDrink.setText(String.valueOf(mainModelDrink.getBestDrink()));
                categoryId.setText(String.valueOf(mainModelDrink.getCategoryId()));
                description.setText(mainModelDrink.getDescription());
                imagePath.setText(mainModelDrink.getImagePath());
                locationId.setText(String.valueOf(mainModelDrink.getLocationId()));
                price.setText(String.valueOf(mainModelDrink.getPrice()));
                priceId.setText(String.valueOf(mainModelDrink.getPriceId()));
                star.setText(String.valueOf(mainModelDrink.getStar()));
                timeId.setText(String.valueOf(mainModelDrink.getTimeId()));
                timeValue.setText(String.valueOf(mainModelDrink.getTimeValue()));
                title.setText(mainModelDrink.getTitle());


                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        boolean isBestDrink = Boolean.parseBoolean(bestDrink.getText().toString());
                        map.put("BestDrink", isBestDrink);
                        map.put("CategoryId", Integer.parseInt(categoryId.getText().toString()));                        map.put("Description",description.getText().toString());
                        map.put("ImagePath",imagePath.getText().toString());
                        map.put("LocationId", Integer.parseInt(locationId.getText().toString()));
                        map.put("Price",mainModelDrink.getPrice());
                        map.put("PriceId", Integer.parseInt(priceId.getText().toString()));
                        map.put("Star",mainModelDrink.getStar());
                        map.put("TimeId", Integer.parseInt(timeId.getText().toString()));
                        map.put("TimeValue", Integer.parseInt(timeValue.getText().toString()));
                        map.put("Title",title.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Drinks")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.imagePath.getContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                                        Toast.makeText(holder.imagePath.getContext(),"Error While Updating",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }


                });


            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.imagePath.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Delete data cannot undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Drinks")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.imagePath.getContext(),"Cancelled.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_drink,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imagePath;
        TextView title,description;

        Button btnEdit,btnDelete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePath=(CircleImageView)itemView.findViewById(R.id.img1);
            title = (TextView) itemView.findViewById(R.id.DrinkText);
            description=(TextView) itemView.findViewById(R.id.DescriptionText);

            btnEdit =(Button) itemView.findViewById(R.id.btnEdit);
            btnDelete=(Button) itemView.findViewById(R.id.btnDelete);

        }
    }
}
