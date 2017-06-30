package com.mybubbles.sdksample;

import static org.junit.Assert.assertEquals;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.ListView;
import com.mybubbles.sdksample.ui.app.MainActivity;
import com.mybubbles.sdksample.ui.scenarios.MyBubblesServiceActivity;
import com.robotium.solo.By;
import com.robotium.solo.Solo;
import com.robotium.solo.WebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SdkTests {

  private static final String USER_ID = "test";
  private static final int NB_SERVICES_WITHOUT_USER_ID = 1;
  private static final int NB_SERVICES_WITH_USER_ID = 2;

  private static final int API_CALL_WAITING_TIME = 1000;
  private static final int SERVICE_OPENING_WAITING_TIME = 5000;
  private static final int PERMISSION_CLICK_WAITING_TIME = 5000;

  private static final String CLOSE_SERVICE_BTN = "closeService";
  private static final String OPEN_URI_BTN = "openURI";
  private static final String ASK_UNIQUE_ID_BTN = "askForUniqueIdPermission";
  private static final String ASK_LOCALIZATION_BTN = "askForLocalizationPermission";
  private static final String OPEN_SERVICE_BTN = "openService";
  private static final String OPEN_FAKE_SERVICE_BTN = "openServiceFalse";
  private static final String ADD_SCREEN_BTN = "addScreen";
  private static final String REMOVE_LAST_SCREEN_BTN = "removeLastScreen";
  private static final String SET_SCREENS_LIST_BTN = "setScreensList";

  private static final String ON_UNIQUE_ID_RECEIVED_FIELD = "onUniqueIdReceived";
  private static final String ON_LOCALIZATION_PERMISSION_CHANGE_FIELD = "onLocalizationPermissionChange";
  private static final String GRANTED_FIELD = "Granted";

  @Rule
  public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

  private Solo solo;

  @Before
  public void setUp() throws Exception {
    // setUp() is run before a test case is started.
    // This is where the solo object is created.
    solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityTestRule.getActivity());
  }

  @After
  public void tearDown() throws Exception {
    // tearDown() is run after a test case has finished.
    // finishOpenedActivities() will finish all the activities that have been opened during the test execution.
    solo.finishOpenedActivities();
  }

  @Test
  public void testNbServicesWithEmptyUserId() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Get the Number of Services
    int nbServices = ((ListView) solo.getView(R.id.services_list_view)).getAdapter().getCount();
    // Assert the Number of Services
    assertEquals(NB_SERVICES_WITHOUT_USER_ID, nbServices);
  }

  @Test
  public void testNbServicesWithTestUserId() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Get the Number of Services
    int nbServices = ((ListView) solo.getView(R.id.services_list_view)).getAdapter().getCount();
    // Assert the Number of Services
    assertEquals(NB_SERVICES_WITH_USER_ID, nbServices);
  }

  @Test
  public void testOpenService() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
  }

  @Test
  public void testOpenServiceThenPressBack() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
    // Press Back
    solo.goBack();
    // Assert that we are back to the Services' List Activity
    solo.assertCurrentActivity("The MainActivity is not open.", MainActivity.class);
  }

  @Test
  public void testOpenServiceThenCloseService() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
    // Click on the Close button
    solo.clickOnWebElement(By.id(CLOSE_SERVICE_BTN));
    // Assert that we are back to the Services' List Activity
    solo.assertCurrentActivity("The MainActivity is not open.", MainActivity.class);
  }

  @Test
  public void testOpenServiceThenAskForUniqueId() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
    // Click on the Unique ID Permission button
    solo.clickOnWebElement(By.id(ASK_UNIQUE_ID_BTN));
    // Wait that the User click on the ALLOW button... :/
    solo.sleep(PERMISSION_CLICK_WAITING_TIME);
    // Get the permission status field
    WebElement onUniqueIdReceivedField = solo.getWebElement(By.id(ON_UNIQUE_ID_RECEIVED_FIELD), 0);
    // Assert that the permission was granted
    assertEquals(GRANTED_FIELD, onUniqueIdReceivedField.getText());
  }

  @Test
  public void testOpenServiceThenAskForLocalization() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
    // Click on the Localization Permission button
    solo.clickOnWebElement(By.id(ASK_LOCALIZATION_BTN));
    // Wait that the User click on the ALLOW button... :/
    solo.sleep(PERMISSION_CLICK_WAITING_TIME);
    // Get the permission status field
    WebElement onLocalizationPermissionChangeField = solo.getWebElement(By.id(ON_LOCALIZATION_PERMISSION_CHANGE_FIELD), 0);
    // Assert that the permission was granted
    assertEquals(GRANTED_FIELD, onLocalizationPermissionChangeField.getText());
  }

  @Test
  public void testOpenServiceThenOpenAnotherService() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
    // Click on the OpenService button
    solo.clickOnWebElement(By.id(OPEN_SERVICE_BTN));
    // Assert that we are still in the Service Activity
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
  }

  @Test
  public void testOpenServiceThenRemoveLastScreen() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
    // Click on the RemoveLastScreen button
    solo.clickOnWebElement(By.id(REMOVE_LAST_SCREEN_BTN));
    // Assert that we are back to the Services' List Activity
    solo.assertCurrentActivity("The MainActivity is not open.", MainActivity.class);
  }

  @Test
  public void testOpenServiceThenAddScreenThenRemoveLastScreenTwice() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
    // Click on the AddScreen button
    solo.clickOnWebElement(By.id(ADD_SCREEN_BTN));
    solo.sleep(500);
    // Click on the RemoveLastScreen button twice
    solo.clickOnWebElement(By.id(REMOVE_LAST_SCREEN_BTN));
    solo.sleep(500);
    solo.clickOnWebElement(By.id(REMOVE_LAST_SCREEN_BTN));
    // Assert that we are back to the Services' List Activity
    solo.assertCurrentActivity("The MainActivity is not open.", MainActivity.class);
  }

  @Test
  public void testOpenServiceThenSetScreensListThenRemoveLastScreenThrice() throws Exception {
    // Empty the User ID EditText
    solo.clearEditText((EditText) solo.getView(R.id.user_id_edit_text));
    // Type User ID in EditText
    solo.enterText((EditText) solo.getView(R.id.user_id_edit_text), USER_ID);
    // Click on Connect button
    solo.clickOnView(solo.getView(R.id.user_id_button));
    // Wait for the API call
    solo.sleep(API_CALL_WAITING_TIME);
    // Click on first row in Services' list
    solo.clickInList(2, 0);
    // Wait that the Service is open & ready
    solo.sleep(SERVICE_OPENING_WAITING_TIME);
    // Assert that the Service Activity is open
    solo.assertCurrentActivity("The MyBubblesServiceActivity is not open.", MyBubblesServiceActivity.class);
    // Click on the SetScreensList button
    solo.clickOnWebElement(By.id(SET_SCREENS_LIST_BTN));
    solo.sleep(500);
    // Click on the RemoveLastScreen button thrice
    solo.clickOnWebElement(By.id(REMOVE_LAST_SCREEN_BTN));
    solo.sleep(500);
    solo.clickOnWebElement(By.id(REMOVE_LAST_SCREEN_BTN));
    solo.sleep(500);
    solo.clickOnWebElement(By.id(REMOVE_LAST_SCREEN_BTN));
    // Assert that we are back to the Services' List Activity
    solo.assertCurrentActivity("The MainActivity is not open.", MainActivity.class);
  }
}
