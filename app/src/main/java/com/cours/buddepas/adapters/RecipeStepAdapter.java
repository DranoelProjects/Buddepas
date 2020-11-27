package com.cours.buddepas.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.cours.buddepas.R;
import com.cours.buddepas.models.Ingredient;
import com.cours.buddepas.models.Step;
import java.util.List;

public class RecipeStepAdapter extends BaseAdapter {
    private List<Step> recipeStepsList;
    LayoutInflater inflater;
    private String TAG = "RecipeStepAdapter";
    private Context context;

    public RecipeStepAdapter(Context context, List<Step> recipeStepsList){
        this.context = context;
        this.recipeStepsList = recipeStepsList;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() { return recipeStepsList.size(); }

    @Override
    public Step getItem(int position) {
        return recipeStepsList.get(position);
    }


    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final ViewHolder holder;
        convertView=null;
        final Step currentStep=getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recipe_step_adapter, null);

            holder.inputStepDescription = convertView
                    .findViewById(R.id.input_recipe_step_description);
            holder.inputStepDescription.setTag(position);
            holder.inputStepDescription.setText(currentStep.getDescription());

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        int tag_position=(Integer) holder.inputStepDescription.getTag();
        holder.inputStepDescription.setId(tag_position);

        holder.inputStepDescription.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                final int position2 = holder.inputStepDescription.getId();
                final EditText caption = (EditText) holder.inputStepDescription;
                if(caption.getText().toString().length()>0){
                    Step newStep = currentStep;
                    currentStep.setDescription(caption.getText().toString());
                    recipeStepsList.set(position2, newStep);
                }else{
                    Toast.makeText(context, "Please enter some value", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        //Init delete btn
        ImageButton deleteBtn = convertView.findViewById(R.id.delete_recipe_step_button);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                recipeStepsList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder {
        EditText inputStepDescription;
    }

    public void AddNewStep(){
        recipeStepsList.add(new Step());
        notifyDataSetChanged();
    }
}
