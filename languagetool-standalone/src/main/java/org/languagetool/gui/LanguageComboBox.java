/* LanguageTool, a natural language style checker 
 * Copyright (C) 2011 Daniel Naber (http://www.danielnaber.de)
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JComboBox;

import org.languagetool.Language;

/**
 * Combo box with list of available languages.
 */
public class LanguageComboBox extends JComboBox<Language> {

  private final ResourceBundle messages;
  private final List<Language> languages = new ArrayList<>();

  public LanguageComboBox(ResourceBundle messages) {
    this.messages = messages;
    populateLanguageBox();
  }

  final void populateLanguageBox() {
    removeAllItems();
    initAllLanguages();
  }

  void selectLanguage(Language language) {
    for (final Language lang : languages) {
      if (lang.toString().equals(language.toString())) {
        setSelectedItem(lang);
      }
    }
  }

  private void initAllLanguages() {
    languages.clear();
    for (Language language : Language.LANGUAGES) {
      final boolean skip = (language == Language.DEMO) || language.hasVariant();
      // TODO: "Simple German" would hide "German (Germany)" - find a proper solution
      final boolean simpleGermanWorkaround = language.getShortNameWithVariant().equals("de-DE");
      if (!skip || simpleGermanWorkaround) {
        languages.add(language);
      }
    }
    Collections.sort(languages, new LanguageComparator(messages));
    for (final Language language : languages) {
      addItem(language);
    }
  }
}
