import UIKit
import PodCompose

@main
class AppDelegate: UIResponder, UIApplicationDelegate {

    
    var viewController = Main_iosKt.MainViewController()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Create a UIWindow instance with the screen bounds
        var window = UIWindow()

        // Set the root view controller to your custom view controller
        window.rootViewController = viewController
        
        window.makeKeyAndVisible()

        return true
    }
}
