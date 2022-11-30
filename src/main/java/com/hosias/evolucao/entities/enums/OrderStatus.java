package com.hosias.evolucao.entities.enums;

public enum OrderStatus {

	WAITING_PAYMENT(1),
	PAID(2),
	SHIPPED(3),
	DELIVERED(4),
	CANCELED(5);
	
	private int code; //codigo do tipo enumerado
	
	//construtor para este codigo do tipo enumerado ele e private por padrao
	
	private OrderStatus(int code) {
		this.code = code;
	}
	// como este metodo é private agora preciso criar um metodo acessivel 
	
	public  int getCode() {
		return code;
	}
	
//metodo estatico (estatico porq ele funciona sem precisar instanciar) que converte um valor em tipo enumerado
	
	public static OrderStatus valueOf(int code) {
		for (OrderStatus value : OrderStatus.values()) {
			if(value.getCode() == code) {
				return value;
			}		
		}
		
		throw new IllegalArgumentException("Invalid orderStatus code");
	}
	
}
