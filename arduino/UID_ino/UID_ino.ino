/*
* Signal     Pin              Pin               Pin
*            Arduino Uno      Arduino Mega      MFRC522 board
* ------------------------------------------------------------
* Reset      9                5                 RST
* SPI SS     10               53                SDA
* SPI MOSI   11               52                MOSI
* SPI MISO   12               51                MISO
* SPI SCK    13               50                SCK
*/

#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

#define SS_PIN 10    //Arduino Uno
#define RST_PIN 9
MFRC522 mfrc522(SS_PIN, RST_PIN);        // Create MFRC522 instance.
MFRC522::MIFARE_Key key;//create a MIFARE_Key struct named 'key', which will hold the card information

const byte ROWS = 4; //four rows
const byte COLS = 4; //four columns

char hexaKeys[ROWS][COLS] = {
  {'1','4','7','*'},
  {'2','5','8','0'},
  {'3','6','9','#'},
  {'A','B','C','D'}
};

byte rowPins[ROWS] = {4, 3, 2, 0}; //connect to the row pinouts of the keypad                     wire from left to right
byte colPins[COLS] = {8, 7, 6, 5}; //connect to the column pinouts of the keypad

Keypad customKeypad = Keypad( makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); 


void setup() 
{
       //Serial.begin(115200);        // Initialize serial communications with the PC
        Serial.begin(2000000);         // Initialize serial communications with Absurd baud rate lol
        SPI.begin();                
        mfrc522.PCD_Init();        
     
        for (byte i = 0; i < 6; i++) {
                key.keyByte[i] = 0xFF;//keyByte is defined in the "MIFARE_Key" 'struct' definition in the .h file of the library
        }       
}

int block=2;//this is the block number we will write into and then read. Do not write into 'sector trailer' block, since this can make the block unusable.
byte readbackblock[18];//This array is used for reading out a block. The MIFARE_Read method requires a buffer that is at least 18 bytes to hold the 16 bytes of a block.
char rfiddata[18];


void loop() 
{
    keyPad();
   
        // Look for new cards
	if ( ! mfrc522.PICC_IsNewCardPresent()) 
        {
         return;
	}

	// Select one of the cards
	if ( ! mfrc522.PICC_ReadCardSerial()) 
        {
          return;
	}
	// Dump debug info about the card; PICC_HaltA() is automatically called
        readBlock();
}


//void printUID()
//{
//  String rfidUid = "";
//
//        for (byte i = 0; i < mfrc522.uid.size; i++) 
//        {
//          rfidUid += String(mfrc522.uid.uidByte[i],HEX);
//        }
//        
//        rfidUid.toUpperCase();
//        Serial.print(rfidUid+"\r\n");
//        mfrc522.PICC_HaltA();
//}

void keyPad()
{
  char customKey = customKeypad.getKey();
  String iden = "\r\n";
  
        if (customKey)
        {
          Serial.print(customKey+iden);
        }
}

void readBlock()
{
         readBlock(block, readbackblock);//read the block back
         
         for (int j=0 ; j<16 ; j++)//print the block contents
         {
          rfiddata[j]  = readbackblock[j]; // put the character in the next free slot.  
         }
         Serial.println(rfiddata);
}
