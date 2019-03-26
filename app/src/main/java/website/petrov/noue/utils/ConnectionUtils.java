package website.petrov.noue.utils;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import java.net.InetSocketAddress;
import java.net.Socket;

public final class ConnectionUtils {
    public interface Consumer {
        void accept(Boolean internet);
    }

    public static class InternetCheck extends AsyncTask<Void, Void, Boolean> {
        @NonNull
        private Consumer mConsumer;

        public InternetCheck(@NonNull Consumer consumer) {
            mConsumer = consumer;
            execute();
        }

        @Override
        @NonNull
        protected Boolean doInBackground(Void... params) {
            boolean result;
            try (Socket sock = new Socket()) {
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                result = sock.isConnected();
            } catch (Exception ignored) {
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(@NonNull Boolean internet) {
            mConsumer.accept(internet);
        }
    }

    private ConnectionUtils() {
    } // hide constructor
}
