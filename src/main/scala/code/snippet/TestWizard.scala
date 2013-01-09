package code.snippet

/**
 * An example of a wizard in Lift
 */
import net.liftweb.http.IntField
import net.liftweb.http.StringField
import net.liftweb.wizard.Wizard
import net.liftweb.http.S
import scala.annotation.target.field
import net.liftweb.util.FieldError
import net.liftweb.util.FieldIdentifier
import net.liftweb.http.LiftScreen
import net.liftweb.util.FieldContainer
import net.liftweb.util.BaseField
import net.liftweb.common.Full

object TestWizard extends Wizard {
  object completeInfo extends WizardVar(false)

  // define the first screen
  val nameAndAge = new Screen {

    // it has a name field
    val name = field(S ? "First Name", "",
                     valMinLen(2, S ? "Name Too Short"),
                     valMaxLen(40, S ? "Name Too Long"))

    // and an age field
    val age = field(S ? "Age", 0, minVal(5, S ? "Too young"),
      maxVal(120, S ? "You should be dead"))

    // choose the next screen based on the age
    override def nextScreen = if (age.is < 18) parentName else favoritePet
  }

  // We ask the parent's name if the person is under 18
  val parentName = new Screen {
    val parentName = field(S ? "Mom or Dad's name", "",
                           valMinLen(2, S ? "Name Too Short"),
      valMaxLen(40, S ? "Name Too Long"))
  }

  // we ask for the favorite pet
  val favoritePet = new Screen {
    val petName = field(S ? "Pet's name", "",
                        valMinLen(2, S ? "Name Too Short"),
                        valMaxLen(40, S ? "Name Too Long"))
  }

  // what to do on completion of the wizard
  def finish() {
    S.notice("Thank you for registering your pet")
    completeInfo.set(true)
  }
}
