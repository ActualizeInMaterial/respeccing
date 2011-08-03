/**
 * 
 * Copyright (C) 2005-2010 AtKaaZ <atkaaz@users.sourceforge.net>
 * Copyright (C) 2005-2010 UnKn <unkn@users.sourceforge.net>
 * 
 * This file and its contents are part of DeMLinks.
 * 
 * DeMLinks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DeMLinks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with DeMLinks. If not, see <http://www.gnu.org/licenses/>.
 */



package org.dml.database.bdb.level1;



import org.dml.tools.*;
import org.references.*;
import org.references.method.*;

import com.sleepycat.db.*;



/**
 * encapsulates the DatabaseConfig and the Database objects into one<br>
 * also makes sure the database isn't open unless it's needed<br>
 * once opened it stays open until done() aka Factory.deInit() is called<br>
 */
public class DatabaseCapsule extends Initer {
	
	private String						dbName;
	private Database					db	= null;
	private DatabaseConfig				dbConf;
	private Level1_Storage_BerkeleyDB	bdbL1;
	
	
	/**
	 * constructor
	 */
	public DatabaseCapsule() {
		super();
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.tools.StaticInstanceTracker#done()
	 */
	@Override
	protected void done( final MethodParams params ) {
		RunTime.assumedNotNull( bdbL1, db );
		try {
			bdbL1.closeAnyPriDB( db );
		} finally {
			db = null;
			bdbL1 = null;
		}
	}
	
	
	
	/**
	 * @param params
	 */
	@Override
	protected void start( final MethodParams params ) {
		// TODO: MethodParams.compulsoryParams(PossibleParams.level1_BDBStorage, PossibleParams.dbName,
		// PossibleParams.priDbConfig_ which means it will throw if those params are not existing
		
		// compulsory
		bdbL1 = (Level1_Storage_BerkeleyDB)params.getEx( PossibleParams.level1_BDBStorage );
		if ( null == bdbL1 ) {
			RunTime.badCall( "missing parameter" );
		}
		RunTime.assumedNotNull( bdbL1 );
		
		// compulsory
		dbName = params.getExString( PossibleParams.dbName );
		RunTime.assumedNotNull( dbName );
		RunTime.assumedFalse( dbName.isEmpty() );
		
		// dbConf is optional / can be null
		final Reference<Object> ref = params.get( PossibleParams.priDbConfig );
		if ( null == ref ) {
			RunTime.badCall( "unspecified parameter" );
		} else {
			dbConf = (DatabaseConfig)ref.getObject();
		}
		
		// open db
		db = bdbL1.openAnyDatabase( dbName, dbConf );
		RunTime.assumedNotNull( db );
	}
	
	
	/**
	 * @return never null
	 */
	public Database getDB() {
		RunTime.assumedTrue( isInitedSuccessfully() );
		RunTime.assumedNotNull( db, bdbL1 );
		return db;// it's never null
	}
	
	
}
