package com.mdi2.c8_103_a3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                RestaurantScreen()
            }
        }
    }
}


@Composable
fun RestaurantScreen(){
    // VARIABLE DECLARATION SECTION
    val vipCustomer = arrayOf("Emma", "Noah", "Olivia")
    val waitingList = remember { mutableStateListOf<String>() }
    val seatedHistory = remember  { mutableStateListOf<String>() }
    var customerName: String by remember {mutableStateOf(value= "") }
    var welcomeMessage: String by remember {mutableStateOf(value= "") }
    val customersTime = remember { mutableStateMapOf<String, String>()}
    // end of VARIABLE DECLARATION SECTION

    Column( // COLUMN1
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ){
        Spacer(modifier = Modifier.height( 30.dp ))
        Text( text = "Restaurant Waiting List", style = MaterialTheme.typography.headlineSmall )

        Spacer( modifier = Modifier.height(12.dp) )
        // input for customer name
        OutlinedTextField( // CUSTOMERNAME
            value = customerName,
            onValueChange = { customerName = it },
            label = { Text("Customer name") },
            modifier = Modifier.fillMaxWidth()
        )// end of outlinedtextfield - CUSTOMERNAME

        Spacer( modifier = Modifier.height( 8.dp ) )

        Button( // ADDCUSTOMER
            onClick = {
                if ( customerName.isNotBlank() ){
                    val newCustomer = customerName
                    val currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))

                    waitingList.add( customerName )
                    customersTime[newCustomer] = currentTime

                    welcomeMessage = "$customerName added to waiting list"
                    customerName = ""
                }else{
                    welcomeMessage = "Please enter a name, can not be blank"
                } // end of if for checking customer name is not blank
            }, // end of onClick
            modifier = Modifier.fillMaxWidth()
        ){ Text( "Add Customer" ) }// end of button - ADDCUSTOMER

        Spacer( modifier = Modifier.height( 8.dp ) )

        Button( // ADDVIPCUSTOMER
            onClick = {
                waitingList.addAll(vipCustomer)
                welcomeMessage = "VIP Customers added"
            },
            modifier = Modifier.fillMaxWidth()
        ){Text( "Load VIP Customers" ) } // end of button - ADDVIPCUSTOMER

        Spacer(modifier = Modifier.height( 8.dp ))

        Button( // SEATCUSTOMER
            onClick = {
                if ( waitingList.isNotEmpty() ){
                    val seatCustomer = waitingList.removeAt( index= 0 )
                    seatedHistory.add( seatCustomer )
                    welcomeMessage= "Party for $seatCustomer is ready to be seated"
                } else {
                    welcomeMessage = "No Customers in waiting line"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ){ Text( "Seat Next Customer" ) } // end of button - SEATCUSTOMER

        Spacer(modifier = Modifier.height( 16.dp ))

        if ( welcomeMessage.isNotBlank() ) {
            Text(text = "Message: $welcomeMessage", style = MaterialTheme.typography.headlineSmall, color=Color.Red)
        } // end of if statement for welcome message

        Spacer(modifier = Modifier.height( 16.dp ))

        Card( // CARD1
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp), // give it a shadow on the back to make it look raised
        ){
            Column(modifier=Modifier.padding( 16.dp )) {
                Text("Waiting List (Queue)")

                Spacer(modifier = Modifier.height( 8.dp ))

                if (waitingList.isNotEmpty()){
                    waitingList.forEachIndexed { index, customerWaiting ->
                        Text("${index + 1}. $customerWaiting")
                    }
                } else{
                    Text("No customers waiting")
                }

            }
        } // end of card - CARD1

        Spacer(modifier = Modifier.height( 16.dp ))

        Card( // CARD2
            modifier = Modifier.fillMaxWidth()
        ){
            Column( modifier = Modifier.padding( 16.dp )){ // inside CARD2
                Text("Seated History (Stack)")

                Spacer(modifier = Modifier.height( 8.dp ))

                if ( seatedHistory.isNotEmpty() ){
                    seatedHistory.reversed().forEach {
                            customerHistory ->
                        Text("- $customerHistory")
                    } // end of reversed loop
                } else {
                    Text("No customers seated yet")
                }
            } // end of column inside CARD2
        } // end of card - CARD2

        Spacer(modifier = Modifier.height( 16.dp ))

        Card( modifier = Modifier.fillMaxWidth() ){
            Column( modifier = Modifier.padding( 16.dp )){
                Text("Customer Arrival Time")
                if (customersTime.isNotEmpty()){
                    customersTime.forEach { (customer, time) ->
                        Text("$customer - Arrived at : $time")
                    }
                } else {
                    Text("No customer data yet")
                }
            }
        }

    } // end of column - COLUMN1
}


//
@Preview(showBackground = true)
@Composable
fun RestaurantScreenPreview(){
    MaterialTheme{
        RestaurantScreen()
    }
}