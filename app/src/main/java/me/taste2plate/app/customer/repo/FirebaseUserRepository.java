package me.taste2plate.app.customer.repo;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;
import me.taste2plate.app.customer.common.CompletionGenericLiveData;
import me.taste2plate.app.customer.common.CompletionLiveData;
import me.taste2plate.app.customer.common.DocumentLiveData;
import me.taste2plate.app.customer.models.User;
import javax.inject.Inject;
import javax.inject.Named;

public class FirebaseUserRepository extends FirebaseRepository {


    private final CollectionReference users;
    private final StorageReference storage;


    @Inject
    public FirebaseUserRepository(@Named("users") CollectionReference users, @Named("storage") StorageReference storage) {
        super(User.class, "users");
        this.users = users;
        this.storage = storage;
    }

    public CompletionGenericLiveData<AuthResult> login(String email, String password) {
        final CompletionGenericLiveData<AuthResult> completion = new CompletionGenericLiveData();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(completion);

        return completion;

    }

    public void logout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }


    public CompletionGenericLiveData<AuthResult> anonymousSignIn() {
        final CompletionGenericLiveData<AuthResult> completion = new CompletionGenericLiveData();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInAnonymously().addOnCompleteListener(completion);

        return completion;

    }

    public CompletionGenericLiveData<AuthResult> signUp(String email, String password) {
        final CompletionGenericLiveData<AuthResult> completion = new CompletionGenericLiveData();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(completion);



        return completion;
    }

    public DocumentLiveData<User> user(final String id) {
        if (id == null) {
            return null;
        }
        final DocumentReference userRef = users.document(id);
        DocumentLiveData<User> data = new DocumentLiveData<>(userRef, User.class);
        userRef.addSnapshotListener(data);
        return data;
    }

    public DocumentLiveData<User> user() {
        final DocumentReference userRef = users.document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DocumentLiveData<User> data = new DocumentLiveData<>(userRef, User.class);
        userRef.addSnapshotListener(data);
        return data;
    }


    public CompletionLiveData addUser(User user, OnSuccessListener successListener, OnFailureListener failureListener) {
        final CompletionLiveData completion = new CompletionLiveData();
        users.add(user).addOnSuccessListener(successListener).addOnFailureListener(failureListener).addOnCompleteListener(completion);

        return completion;
    }

    public CompletionLiveData update(User user) {
        final CompletionLiveData completion = new CompletionLiveData();
        users.document(user.getId()).set(user).addOnCompleteListener(completion);
        return completion;
    }



}
