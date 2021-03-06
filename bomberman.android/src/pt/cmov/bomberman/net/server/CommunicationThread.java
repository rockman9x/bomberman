package pt.cmov.bomberman.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;
import pt.cmov.bomberman.model.GameLevel;

public class CommunicationThread implements Runnable {
	private Socket clientSocket;
	private int player_id;

	public CommunicationThread(Socket clientSocket, int player_id) {
		this.clientSocket = clientSocket;
		this.player_id = player_id;
	}

	public void run() {
    		try {
    			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    			
    			RemotePlayer remotePlayer = new RemotePlayer(player_id, out);
    			GameLevel.getInstance().getBoard().addNewPlayer(remotePlayer);
    			
    			Server.getInstance().sendPlayerId(remotePlayer);
    			String inputLine;
    			
    			while ((inputLine = in.readLine()) != null) {
    				Log.d("SERVER_REQUEST", inputLine);
    				String[] tokens = inputLine.split(" ");
    				String result = Server.getInstance().parse_msg(tokens);
    				
    				if(result != null){
    					String command = tokens[0];
    					Server.getInstance().sendClientReply(remotePlayer, command+"_response", result);
    				}
    				
    				if(GameLevel.getInstance().isGameOver())
    					break;
    			}
    			
    			Server.getInstance().delClient(remotePlayer);
    		} 
    		catch (IOException ioEx) {
    			ioEx.printStackTrace();
    			
    			try {
    				Log.d("CLIENT_SOCKET", "Closing client socket for player id '"+player_id+"'.");
					clientSocket.close();
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
    		}
		}
}
