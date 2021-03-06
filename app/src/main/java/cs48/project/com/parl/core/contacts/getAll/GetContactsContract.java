package cs48.project.com.parl.core.contacts.getAll;

import java.util.List;

import cs48.project.com.parl.models.User;

/**
 * Created by jakebliss on 5/7/17.
 */

public interface GetContactsContract {
    interface View {
        void onGetContactsUsersSuccess(List<User> uids);

        void onGetContactsUsersFailure(String message);
    }

    interface Presenter {
        void getContactsUsers();

    }

    interface Interactor {
        void getContactsUsersFromFirebase();

    }

    interface OnGetContactsUsersListener {
        void onGetContactsUsersSuccess(List<User> uids);
        void onGetContactsUsersFailure(String message);
    }
}
