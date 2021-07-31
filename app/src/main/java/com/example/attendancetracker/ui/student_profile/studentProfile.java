package com.example.attendancetracker.ui.student_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.attendancetracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class studentProfile extends Fragment {

    private TextView _set_UID,_set_nameS,_set_phoneS,_set_emailS,_set_college,_set_dept,_set_year,_set_sem,_set_emailP,_set_phoneP;
    private FirebaseAuth pAuthS;
    private DatabaseReference databaseReferenceS,database_emailS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_profile, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        _set_UID=(TextView)getView().findViewById(R.id.set_UID);
        _set_nameS=(TextView)getView().findViewById(R.id.set_nameS);
        _set_emailS=(TextView)getView().findViewById(R.id.set_emailS);
        _set_phoneS=(TextView)getView().findViewById(R.id.set_phoneS);

        _set_college=(TextView)getView().findViewById(R.id.set_college);
        _set_dept=(TextView)getView().findViewById(R.id.set_dept);
        _set_year=(TextView)getView().findViewById(R.id.set_year);
        _set_sem=(TextView)getView().findViewById(R.id.set_sem);

        _set_emailP=(TextView)getView().findViewById(R.id.set_emailP);
        _set_phoneP=(TextView)getView().findViewById(R.id.set_phoneP);

        pAuthS = FirebaseAuth.getInstance();
        databaseReferenceS=FirebaseDatabase.getInstance().getReference().child("Student_Details").child(pAuthS.getUid());
        database_emailS=FirebaseDatabase.getInstance().getReference().child("Users").child(pAuthS.getUid());



        databaseReferenceS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String valueUID=dataSnapshot.child("uid").getValue().toString();
                _set_UID.setText("UID: "+valueUID);
                String valueNameS=dataSnapshot.child("name").getValue().toString();
                _set_nameS.setText("Name: "+valueNameS);
                String valueCollege=dataSnapshot.child("college").getValue().toString();
                _set_college.setText("College: "+valueCollege);
                String valueDept=dataSnapshot.child("dept").getValue().toString();
                _set_dept.setText("Department: "+valueDept);
                String valueYear=dataSnapshot.child("year").getValue().toString();
                _set_year.setText("Year: "+valueYear);
                String valueSem=dataSnapshot.child("semester").getValue().toString();
                _set_sem.setText("Semester: "+valueSem);
                String valueEmailP=dataSnapshot.child("parent_Email").getValue().toString();
                _set_emailP.setText("Parent's Email-ID: "+valueEmailP);
                String valuePhoneP=dataSnapshot.child("parent_Phone").getValue().toString();
                _set_phoneP.setText("Parent's Phone No: "+valuePhoneP);
                String valuePhoneS=dataSnapshot.child("student_Phone").getValue().toString();
                _set_phoneS.setText("Student's Phone No: "+valuePhoneS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database_emailS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String valueEmail=dataSnapshot.child("email").getValue().toString();
                _set_emailS.setText("Student's Email-ID: "+valueEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


/*  Query query=FirebaseDatabase.getInstance().getReference().child("Student");
        FirebaseListOptions<Student> options=new FirebaseListOptions.Builder<Student>()
                .setLayout(R.layout.studentdisplay)
                .setLifecycleOwner(studentProfile.this)
                .setQuery(query,Student.class)
                .build();
        adapter=new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {

                TextView stid=getActivity().findViewById(R.id.stuid);
                TextView stName=getActivity().findViewById(R.id.stname);
                TextView strollno=getActivity().findViewById(R.id.strollno);
                TextView stmobile=getActivity().findViewById(R.id.stmobile);

                Student d=(Student) model;
                stid.setText("Student Id"+d.getEmpCode().toString().trim());
                stName.setText("Student Name"+d.getName().toString().trim());
                strollno.setText("Student Roll No"+d.getRollno().toString().trim());
                stmobile.setText("Student Mobile No"+d.getPhone().toString().trim());

            }


        };
        lv.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    } */


/*   ListView dataListView;
    DatabaseReference databaseReference;
    FirebaseUser user;
    ArrayList<String> list;
    String uid;
    private EditText itemText;
    Button deleteButton,findButton;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private Boolean searchMode = false;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;*/

/*
 findButton =  getView().findViewById(R.id.findButton);
        final Button deleteButton = getView().findViewById(R.id.deleteButton);
        deleteButton.setEnabled(false);
        itemText = (EditText) getView().findViewById(R.id.text3);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findItems();
            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");


        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_single_choice, listItems);
        dataListView.setAdapter(adapter);
        dataListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        dataListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        selectedPosition = position;
                        itemSelected = true;
                        deleteButton.setEnabled(true);
                    }
                });
        addChildEventListener();



        return root;
    }


    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseReference.addChildEventListener(childListener);
    }


    public void deleteItem(View view) {
        dataListView.setItemChecked(selectedPosition, false);
        databaseReference.child(listKeys.get(selectedPosition)).removeValue();
    }

    public void findItems() {

        Query query;

        if (!searchMode) {
            findButton.setText("Clear");
            query = databaseReference.orderByChild("email").
                    equalTo(itemText.getText().toString());
            searchMode = true;
        } else {
            searchMode = false;
            findButton.setText("Find");
            query = databaseReference.orderByKey();
        }

        if (itemSelected) {
            dataListView.setItemChecked(selectedPosition, false);
            itemSelected = false;
            deleteButton.setEnabled(false);
        }
    }

    ValueEventListener queryValueListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
            Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

            adapter.clear();
            listKeys.clear();

            while (iterator.hasNext()) {
                DataSnapshot next = (DataSnapshot) iterator.next();

                String match = (String) next.child("description").getValue();
                String key = next.getKey();
                listKeys.add(key);
                adapter.add(match);
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
*/



/*   databaseReference= FirebaseDatabase.getInstance().getReference("Users").child("email");
         l1=getView().findViewById(R.id.list);
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        list=new ArrayList<>();

        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
        l1.setAdapter(adapter);
       databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                String n=dataSnapshot.child(uid).child("email").getValue(String.class);
              String _uid=dataSnapshot.child(uid).child("type").getValue(String.class);
                String rollNo=dataSnapshot.child(uid).child("rollNo").getValue(String.class);
                String phone=dataSnapshot.child(uid).child("mobile").getValue(String.class);
   */  /*                         list.add(n);
                        list.add(_uid);
                        list.add(rollNo);
                        list.add(phone);




@Override
public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(getContext(),"Error occurred",Toast.LENGTH_LONG).show();
        }
        });


        databaseReference.addChildEventListener(new ChildEventListener() {
@Override
public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        String value=dataSnapshot.getValue(String.class);
        list.add(value);
        adapter.notifyDataSetChanged();
        }

@Override
public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

@Override
public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

@Override
public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

@Override
public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        });

        shareViewModel =
        ViewModelProviders.of(this).get(ShareViewModel.class);

final TextView textView = root.findViewById(R.id.text_tools);
        shareViewModel.getText().observe(this, new Observer<String>() {
@Override
public void onChanged(@Nullable String s) {
        textView.setText(s);
        }
        });
        */