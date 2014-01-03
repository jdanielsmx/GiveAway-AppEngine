package com.example.giveaway;

import com.example.giveaway.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "posteditemendpoint", namespace = @ApiNamespace(ownerDomain = "example.com", ownerName = "example.com", packagePath = "giveaway"))
public class PostedItemEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPostedItem")
	public CollectionResponse<PostedItem> listPostedItem(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<PostedItem> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from PostedItem as PostedItem");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<PostedItem>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (PostedItem obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<PostedItem> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPostedItem")
	public PostedItem getPostedItem(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		PostedItem posteditem = null;
		try {
			posteditem = mgr.find(PostedItem.class, id);
		} finally {
			mgr.close();
		}
		return posteditem;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param posteditem the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPostedItem")
	public PostedItem insertPostedItem(PostedItem posteditem) {
		EntityManager mgr = getEntityManager();
		try {
			//if (containsPostedItem(posteditem)) {
				//throw new EntityExistsException("Object already exists");
			//}
			mgr.persist(posteditem);
		} finally {
			mgr.close();
		}
		return posteditem;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param posteditem the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePostedItem")
	public PostedItem updatePostedItem(PostedItem posteditem) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPostedItem(posteditem)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(posteditem);
		} finally {
			mgr.close();
		}
		return posteditem;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePostedItem")
	public void removePostedItem(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			PostedItem posteditem = mgr.find(PostedItem.class, id);
			mgr.remove(posteditem);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPostedItem(PostedItem posteditem) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			PostedItem item = mgr.find(PostedItem.class, posteditem.getKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
