package com.example.demo;
import com.example.demo.DigitalArt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber; 
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion; // xac+
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5; //xac+
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.RawTransactionManager;
import java.math.BigInteger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import com.example.demo.LoggingController;
import java.lang.Exception;
import io.sentry.Sentry;

@SpringBootApplication
@RestController  
public class DemoApplication {
	private Log log = LogFactory.getLog(LoggingController.class);

	// Web3j web3 = Web3j.build(new HttpService("http://192.168.59.107:8545"));  // Minikube : defaults to http://localhost:8545/

	// Web3j web3 = Web3j.build(new HttpService("http://XX.255.207.90:8545"));  // defaults to http://localhost:8545/
	/// Web3j web3 = Web3j.build(new HttpService("http://localhost:8545"));  // GOOD : defaults to http://localhost:8545/
	Web3j web3 = Web3j.build(new HttpService("http://192.168.59.110:30545"));  // GOOD : defaults to http://localhost:8545/

	// Web3j web3 = Web3j.build(new HttpService("http://XX.72.222.91:8545"));  // Tony defaults to http://localhost:8545/
	// some made up credentials (maybe create from user session)
	String alicePrivKey = "0x5094f257d3462083bcbc02c61d98c038cfa71cdd497834c5f38cd75010ddb7a5";
	Credentials alice = Credentials.create(alicePrivKey);
	// Credentials alice = Credentials.create("0x5094f257d3462083bcbc02c61d98c038cfa71cdd497834c5f38cd75010ddb7a5");
	Credentials bob = Credentials.create("0x78785c4ab4ba44b83509296af86af56ff00db79ba26a292d0556a4b4e8cea87c");
	Credentials charlie = Credentials.create("0x417fbb670417375f2916a4b0110dc7d68d81ea15aad3e6eb69f166b5bed6503f");




    String localContractAddress = "";

	public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {


		System.out.println("Starting DemoApplication!");



		SpringApplication.run(DemoApplication.class, args);

		System.out.println("VMware Blockchain Ethereum ERC-721 NFT Demo started!!");
	}



	@GetMapping // makes this a REST endpoint
	public String hello() { // XAC+

		System.out.println("GET : hello or anything at localhost:port");
		log.info("Basic catch all GET endpoint");





		try {
			throw new Exception("XAC Sentry.IO. exception in hello()");
		} catch (Exception e) {
			Sentry.captureException(e);
		}

		return "Welcome to da VMware Blockchain Ethereum ERC-721 NFT Demo, running on Project Concord (local).";
	}

	@GetMapping(value="/deploy")
    public String  deploy() {

		try {

			    // deploy the same code again to a new contract per student session
			System.out.println("DEPLOY #1");
			TransactionManager transactionManager = new RawTransactionManager(web3, alice, 5000);
			System.out.println("DEPLOY getFromAddress :" + transactionManager.getFromAddress());
			System.out.println("DEPLOY #2");
			System.out.println("DefaultGasProvider GAS_PRICE" + DefaultGasProvider.GAS_PRICE);
			System.out.println("DefaultGasProvider  GAS_LIMIT" + DefaultGasProvider.GAS_LIMIT);
			System.out.println("DEPLOY PROBLEM IS HERE ->");
			// if concord is not running or connected to properly, will see
			// Exception deploying contractjava.lang.RuntimeException: java.net.ConnectException:
			// Failed to connect to localhost/0:0:0:0:0:0:0:1:8545
			DigitalArt localContract = DigitalArt.deploy(web3, transactionManager, new DefaultGasProvider()).send();
			// xac + DigitalArt localContract = DigitalArt.deploy(web3,transactionManager, credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT).send();
			System.out.println("DEPLOY #3");
			localContractAddress = localContract.getContractAddress();

			System.out.println("DEPLOY #4");
	
		} catch (Exception e) {
			System.out.println("Exception deploying contract" + e);
			// Sentry.captureException(e);

		}


        return localContractAddress;
    }

	@GetMapping(value="/mint")
    public String  mint(@RequestParam String title,@RequestParam String text,@RequestParam String author) {

		if (localContractAddress.equals("")){
			return "not deployed";
		}

		TransactionReceipt receipt = new TransactionReceipt();

		try {

			TransactionManager transactionManager = new RawTransactionManager(web3, alice, 5000);
			DigitalArt contract = DigitalArt.load( localContractAddress, web3, transactionManager, new DefaultGasProvider());
			receipt = contract.mint(title,text,author).send();
			
		} catch (Exception e) {
			System.out.println("Exception minting new token" + e);
			Sentry.captureException(e);

		}


        return receipt.toString();
    }

	@GetMapping(value="/{user}")
    public String  userAddress(@PathVariable String user) {

		String address = "undefined";
		System.out.println("GET address of : ");

		System.out.println("GET address of " + user.toLowerCase());
		switch (user.toLowerCase()){
			case "alice": address = alice.getAddress(); break;
			case "bob" : address = bob.getAddress(); break;
			case "charlie" : address = charlie.getAddress(); break;
		}
		
        return address;
    }

	@GetMapping(value="/test")
	public String test() {

		String msg = "TEST TEST TEST!";
		System.out.println(msg);


		System.out.println("WEB3J.CREDENTIALS");
		System.out.println("Alice's private key" + 	alicePrivKey);
		System.out.println("Alice's credentails" + 	alice.getAddress());
		System.out.println("Alice's hashcode" + 	alice.hashCode());

		return msg;

	}
	@GetMapping(value="/{user}/balance")
    public String  userBalance(@PathVariable String user) {

		String address = "undefined";
		switch (user.toLowerCase()){
			case "alice": address = alice.getAddress(); break;
			case "bob" : address = bob.getAddress(); break;
			case "charlie" : address = charlie.getAddress(); break;
		}

		String output = "";
		BigInteger balance = BigInteger.valueOf(0);
		TransactionReceipt receipt = new TransactionReceipt();
		try {

			TransactionManager transactionManager = new RawTransactionManager(web3, alice, 5000);
			DigitalArt contract = DigitalArt.load( localContractAddress, web3, transactionManager, new DefaultGasProvider());
			balance = contract.balanceOf(address).send();
			output += "balance:" + balance +"\n";

			for (BigInteger bi = BigInteger.ZERO;
            	bi.compareTo(balance) < 0;
            	bi = bi.add(BigInteger.ONE)) {
					BigInteger tokenId = contract.tokenByIndex(bi).send();
					//xac-Tuple4<String, String, String, BigInteger> nft = contract.DigitalArtArr(tokenId).send();
					Tuple5<String, String, String, BigInteger, String> nft = contract.DigitalArtArr(tokenId).send(); //xac+

					output += "poemTitle:" + nft.component1() +";";
					output += "poemBody:" + nft.component2() +";";
					output += "author:" + nft.component3() +";";
					// output += "tokenId:" + nft.component4() +";\n"; // xac-
					output += "tokenId:" + nft.component4() +";";
					output += "ipfsHash:" + nft.component5() +";\n"; //xac+
		
				}
			
		} catch (Exception e) {
			System.out.println("Exception minting new token" + e);
			Sentry.captureException(e);

		}

        return output;
    }

	@GetMapping(value="/{user}/tokens/{tokenId}/transfer")
    public String transfer(@PathVariable("user") String user, @PathVariable("tokenId") Long tokenId, @RequestParam String transferToAddress) {

		Credentials userCredentials ;
		switch (user.toLowerCase()){
			case "alice": userCredentials = alice; break;
			case "bob" : userCredentials = bob; break;
			case "charlie" : userCredentials = charlie; break;
			default: userCredentials = alice; break; 
		}

		TransactionReceipt receipt = new TransactionReceipt();

		try {

			TransactionManager transactionManager = new RawTransactionManager(web3, userCredentials, 5000);
			DigitalArt contract = DigitalArt.load( localContractAddress, web3, transactionManager, new DefaultGasProvider());
			receipt = contract.safeTransferFrom(userCredentials.getAddress(), transferToAddress, BigInteger.valueOf(tokenId)).send();
			System.out.println("receipt " + receipt);
		
		 } catch (Exception e) {
		 	System.out.println(e);
			Sentry.captureException(e);

		}

        return receipt.toString();
    }

}
