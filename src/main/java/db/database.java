package com.decentralbank.decentralj.db;

   interface Db {

    public void put(byte[] key, byte[] value);
    public byte[] get(byte[] key);
    public void delete(byte[] key);

    public void close();
}

public class database implements Db {
	
	private Db db;
	private String name;

	@Override
	public void put(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(byte[] key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
	

}
