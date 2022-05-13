
import com.project.picpicker.Dependency.hilt
import com.project.picpicker.Dependency.retrofit
import com.project.picpicker.Modules.retrofitModule
import com.project.picpicker.dependency.helper.addDep
import com.project.picpicker.dependency.helper.module
import com.project.picpicker.dependency.helper.plus
import com.project.picpicker.plugins.config.module

module(
    enabledCompose = false,
    appDependency = addDep(
        retrofit,
        *hilt,
    ) + addDep(
        module(retrofitModule)
    )
)