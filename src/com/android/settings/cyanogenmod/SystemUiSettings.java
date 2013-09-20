/*
 * Copyright (C) 2012 The CyanogenMod project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.cyanogenmod;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManagerGlobal;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class SystemUiSettings extends SettingsPreferenceFragment  implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "SystemSettings";

    private static final String FORCE_NAVIGATION_BAR = "force_navigation_bar";
//    private static final String MENU_BUTTON_ENABLED = "menu_button_enabled";
    private static final String BACK_BUTTON_ENABLED = "back_button_enabled";

    private static final String CATEGORY_NAVBAR = "navigation_bar";
    private static final String KEY_PIE_CONTROL = "pie_control";

    private CheckBoxPreference mForceNavigationBar;
//    private CheckBoxPreference mMenuButtonEnabled;
    private CheckBoxPreference mBackButtonEnabled;

    private PreferenceScreen mPieControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.system_ui_settings);
        PreferenceScreen prefScreen = getPreferenceScreen();

        mForceNavigationBar = (CheckBoxPreference) findPreference(FORCE_NAVIGATION_BAR);
            mForceNavigationBar.setOnPreferenceChangeListener(this);
//        mMenuButtonEnabled = (CheckBoxPreference) findPreference(MENU_BUTTON_ENABLED);
//            mMenuButtonEnabled.setOnPreferenceChangeListener(this);
        mBackButtonEnabled = (CheckBoxPreference) findPreference(BACK_BUTTON_ENABLED);
            mBackButtonEnabled.setOnPreferenceChangeListener(this);

        mPieControl = (PreferenceScreen) findPreference(KEY_PIE_CONTROL);
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePieControlSummary();
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mForceNavigationBar) {
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.DEV_FORCE_SHOW_NAVBAR,
                    ((CheckBoxPreference)preference).isChecked() ? 0 : 1);
            return true;
//        } else if (preference == mMenuButtonEnabled) {
//            Settings.System.putInt(getActivity().getContentResolver(),
//                    Settings.System.DEV_MENU_BUTTON_ENABLED,
//                    ((CheckBoxPreference)preference).isChecked() ? 0 : 1);
//            return true;
        } else if (preference == mBackButtonEnabled) {
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.DEV_BACK_BUTTON_ENABLED,
                    ((CheckBoxPreference)preference).isChecked() ? 0 : 1);
            return true;
        }
        return false;
    }

    private void updatePieControlSummary() {
        if (mPieControl != null) {
            boolean enabled = Settings.System.getInt(getContentResolver(),
                Settings.System.PIE_CONTROLS, 0) != 0;

            if (enabled) {
                mPieControl.setSummary(R.string.pie_control_enabled);
            } else {
                mPieControl.setSummary(R.string.pie_control_disabled);
            }
        }
    }
}
