package cn.dozyx.template.system

import cn.dozyx.template.base.BaseTestActivity

/**
 * screenOrientation 关于竖屏有是多个值：
 * landscape: 一般的竖屏，将屏幕旋转 180 度，显示也会倒着
 * reverseLandscape: 方向与普通的竖屏相反
 * sensorLandscape: 跟随传感器变化，比如旋转 180 度，显示也会跟着旋转
 * userLandscape: 基于传感器和用户偏好来选择时普通的竖屏还是反过来的竖屏
 *
 */
class LandscapeActivity : BaseTestActivity() {
    override fun initActions() {

    }
}