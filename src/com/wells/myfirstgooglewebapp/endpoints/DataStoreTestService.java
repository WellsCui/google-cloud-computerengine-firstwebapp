package com.wells.myfirstgooglewebapp.endpoints;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.services.datastore.DatastoreV1.*;
import com.google.api.services.datastore.client.Datastore;
import com.google.api.services.datastore.client.DatastoreException;
import com.google.api.services.datastore.client.DatastoreFactory;
import com.google.api.services.datastore.client.DatastoreHelper;
import com.google.api.services.datastore.client.DatastoreOptions;
import com.google.protobuf.ByteString;

@Api(name = "dataStoreService", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
		Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID,
		Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE })
public class DataStoreTestService {

	public static Map<String, Commodity> commodities = new HashMap<String, Commodity>();

	static {
		

//		 Commodity commodity=new Commodity();
//		 commodity.setId("001");
//		 commodity.setName("iPhone V");
//		 commodity.setPrice(200);
//		 commodities.put(commodity.getId(), commodity);

	}

	private static void AddEntityToDatasource()
			throws GeneralSecurityException, IOException, DatastoreException {
		Datastore datastore = DatastoreFactory.get().create(
				DatastoreHelper.getOptionsfromEnv().dataset("Adams").build());

		// Create an RPC request to begin a new transaction.
		BeginTransactionRequest.Builder treq = BeginTransactionRequest
				.newBuilder();
		// Execute the RPC synchronously.
		BeginTransactionResponse tres = datastore
				.beginTransaction(treq.build());
		// Get the transaction handle from the response.
		ByteString tx = tres.getTransaction();
		CommitRequest.Builder creq = CommitRequest.newBuilder();
		// Set the transaction to commit.
		creq.setTransaction(tx);
		Entity entity = BuildEntity();
		// Insert the entity in the commit request mutation.
		creq.getMutationBuilder().addInsert(entity);

		// Execute the Commit RPC synchronously and ignore the response.
		// Apply the insert mutation if the entity was not found and close
		// the transaction.
		datastore.commit(creq.build());

	}

	private static Entity GetEntityFromDatasource()
			throws GeneralSecurityException, IOException, DatastoreException {
		Datastore datastore = DatastoreFactory.get().create(
				DatastoreHelper.getOptionsfromEnv().dataset("Adams").build());

		// Create an RPC request to begin a new transaction.
		BeginTransactionRequest.Builder treq = BeginTransactionRequest
				.newBuilder();
		// Execute the RPC synchronously.
		BeginTransactionResponse tres = datastore
				.beginTransaction(treq.build());
		// Get the transaction handle from the response.
		ByteString tx = tres.getTransaction();
		CommitRequest.Builder creq = CommitRequest.newBuilder();
		// Set the transaction to commit.
		creq.setTransaction(tx);
		// Create an RPC request to get entities by key.
		LookupRequest.Builder lreq = LookupRequest.newBuilder();
		// Set the entity key with only one `path_element`: no parent.
		Key.Builder key = Key.newBuilder().addPathElement(
				Key.PathElement.newBuilder().setKind("Trivia").setName("hgtg"));
		// Add one key to the lookup request.
		lreq.addKey(key);
		// Set the transaction, so we get a consistent snapshot of the
		// entity at the time the transaction started.
		lreq.getReadOptionsBuilder().setTransaction(tx);
		// Execute the RPC and get the response.
		LookupResponse lresp = datastore.lookup(lreq.build());

		Entity entity=null;
		if (lresp.getFoundCount() > 0) {
			entity = lresp.getFound(0).getEntity();

		}
		
		datastore.commit(creq.build());
		return entity;
	}

	private static Entity BuildEntity() {
		Entity entity;
		Entity.Builder entityBuilder = Entity.newBuilder();
		// Set the entity key with only one `path_element`: no parent.
		Key.Builder key = Key.newBuilder().addPathElement(
				Key.PathElement.newBuilder().setKind("Trivia").setName("hgtg"));

		// Set the entity key.
		entityBuilder.setKey(key);
		// Add two entity properties:
		// - a utf-8 string: `question`
		entityBuilder
				.addProperty(Property
						.newBuilder()
						.setName("question")
						.setValue(
								Value.newBuilder().setStringValue(
										"Meaning of Life?")));
		// - a 64bit integer: `answer`
		entityBuilder.addProperty(Property.newBuilder().setName("answer")
				.setValue(Value.newBuilder().setIntegerValue(42)));
		// Build the entity.
		entity = entityBuilder.build();
		return entity;
	}

	public Commodity getCommodity(@Named("id") String id) {
		
		try {
			AddEntityToDatasource();
			Entity entity = GetEntityFromDatasource();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatastoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return commodities.get(id);
	}
}
