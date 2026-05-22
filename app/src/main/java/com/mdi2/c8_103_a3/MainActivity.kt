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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdi2.c8_103_a3.ui.theme.C8103A3Theme

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
    //
    val vipCustomer = arrayOf("Emma", "Noah", "Olivia")
    val waitingList = remember { mutableStateListOf<String>() }
    val seatedHistory = remember  { mutableStateListOf<String>() }
    var customerName: String by remember {mutableStateOf(value= "") }
    var welcomeMessage: String by remember {mutableStateOf(value= "") }


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
                    waitingList.add( customerName )
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
    } // end of column - COLUMN1
}



@Preview(showBackground = true)
@Composable
fun RestaurantScreenPreview(){
    MaterialTheme{
        RestaurantScreen()
    }
}