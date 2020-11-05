package com.example.guidesign.ui.gallery;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.guidesign.R;
import com.example.guidesign.deriction;


public class showaction extends Fragment {
  int act;


    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_showaction, container, false);
      final ImageView imageView = root.findViewById(R.id.imageView2);
      TextView textView = root.findViewById(R.id.action_name);
      Bundle bundle = getArguments();

       int str =bundle.getInt("position") ;
       if (str == 0){
          act = 1;
          textView.setText("摸同側耳朵");

          Glide.with(this).asGif().load(R.drawable.a1).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
              return false;
            }

            @Override
            public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
              resource.setLoopCount(1);

              return false;
            }
          }).into(imageView);
         imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             Glide.with(showaction.this).asGif().load(R.drawable.a1).listener(new RequestListener<GifDrawable>() {
               @Override
               public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                 return false;
               }

               @Override
               public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                 resource.setLoopCount(1);

                 return false;
               }
             }).into(imageView);

           }
         });


        }
      if (str == 1){
        act = 2;
        textView.setText("摸對側膝蓋");
        Glide.with(this).asGif().load(R.drawable.a2).listener(new RequestListener<GifDrawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            resource.setLoopCount(1);

            return false;
          }
        }).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Glide.with(showaction.this).asGif().load(R.drawable.a2).listener(new RequestListener<GifDrawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
              }

              @Override
              public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);

                return false;
              }
            }).into(imageView);

          }
        });
      }
      if (str == 2){
        act = 3;
        textView.setText("摸屁股");
        Glide.with(this).asGif().load(R.drawable.a3).listener(new RequestListener<GifDrawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            resource.setLoopCount(1);

            return false;
          }
        }).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Glide.with(showaction.this).asGif().load(R.drawable.a3).listener(new RequestListener<GifDrawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
              }

              @Override
              public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);

                return false;
              }
            }).into(imageView);

          }
        });
      }
      if (str == 3){
        act = 4;
        textView.setText("手向前抬-手肘伸直");
        Glide.with(this).asGif().load(R.drawable.a4).listener(new RequestListener<GifDrawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            resource.setLoopCount(1);

            return false;
          }
        }).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Glide.with(showaction.this).asGif().load(R.drawable.a4).listener(new RequestListener<GifDrawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
              }

              @Override
              public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);

                return false;
              }
            }).into(imageView);

          }
        });
      }
      if (str == 4){
        act = 5;
        textView.setText("翻掌-手肘彎曲");
        Glide.with(this).asGif().load(R.drawable.a5).listener(new RequestListener<GifDrawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            resource.setLoopCount(1);

            return false;
          }
        }).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Glide.with(showaction.this).asGif().load(R.drawable.a5).listener(new RequestListener<GifDrawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
              }

              @Override
              public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);

                return false;
              }
            }).into(imageView);

          }
        });
      }
      if (str == 5){
        act = 6;
        textView.setText("手向側邊平舉-手肘伸直");
        Glide.with(this).asGif().load(R.drawable.a6).listener(new RequestListener<GifDrawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            resource.setLoopCount(1);

            return false;
          }
        }).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Glide.with(showaction.this).asGif().load(R.drawable.a6).listener(new RequestListener<GifDrawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
              }

              @Override
              public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);

                return false;
              }
            }).into(imageView);

          }
        });
      }
      if (str == 6){
        act = 7;
        textView.setText("手向上抬-手肘伸直");
        Glide.with(this).asGif().load(R.drawable.a7).listener(new RequestListener<GifDrawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            resource.setLoopCount(1);

            return false;
          }
        }).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Glide.with(showaction.this).asGif().load(R.drawable.a7).listener(new RequestListener<GifDrawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
              }

              @Override
              public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);

                return false;
              }
            }).into(imageView);

          }
        });
      }
      if (str == 7){
        act = 8;
        textView.setText("翻掌-手肘伸直");
        Glide.with(this).asGif().load(R.drawable.a8).listener(new RequestListener<GifDrawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            return false;
          }

          @Override
          public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            resource.setLoopCount(1);

            return false;
          }
        }).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Glide.with(showaction.this).asGif().load(R.drawable.a8).listener(new RequestListener<GifDrawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
              }

              @Override
              public boolean onResourceReady(final GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);

                return false;
              }
            }).into(imageView);

          }
        });
      }





      configureImageButton(root);


        return root;
    }

  private void configureImageButton(View root) {
    ImageButton s1 = root.findViewById(R.id.imageButton);
    s1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        deriction fragment = new deriction();
        Bundle bundle = new Bundle();
        bundle.putString("act", String.valueOf(act));
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
  }


}





