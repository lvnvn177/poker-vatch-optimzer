package com.sellanding.poker_vatch_optimzer;

import org.springframework.boot.SpringApplication;

public class TestPokerVatchOptimzerApplication {

	public static void main(String[] args) {
		SpringApplication.from(PokerVatchOptimzerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
