/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.facebook.samples.lithocodelab;

import static com.facebook.yoga.YogaAlign.STRETCH;
import static com.facebook.yoga.YogaEdge.ALL;
import static com.facebook.yoga.YogaEdge.BOTTOM;
import static com.facebook.yoga.YogaEdge.END;
import static com.facebook.yoga.YogaEdge.HORIZONTAL;
import static com.facebook.yoga.YogaEdge.TOP;
import static com.facebook.yoga.YogaJustify.CENTER;

import android.graphics.Color;
import com.facebook.litho.Border;
import com.facebook.litho.Column;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Row;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Image;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaAlign;

/**
 * Renders a "story card" with a header and message. You should also implement a togglable "saved"
 * state.
 */
@LayoutSpec
public class StoryCardComponentSpec {

  static final int CARD_INSET = 12;
  static final int CARD_INTERNAL_PADDING = 7;

  @OnCreateLayout
  static ComponentLayout onCreateLayout(
      ComponentContext c,
      @Prop Component<?> header,
      @Prop String content) {
    return Column.create(c)
        .backgroundColor(Color.WHITE)
        .child(header)
        .child(
            Text.create(c, 0, R.style.message_text)
                .text(content)
                .withLayout()
                .paddingDip(HORIZONTAL, CARD_INSET)
                .paddingDip(BOTTOM, CARD_INTERNAL_PADDING))
        .child(
            Row.create(c)
                .alignSelf(STRETCH)
                .paddingDip(HORIZONTAL, CARD_INSET)
                .paddingDip(BOTTOM, CARD_INTERNAL_PADDING)
                .paddingDip(TOP, CARD_INTERNAL_PADDING)
                .justifyContent(CENTER)
                .child(
                    Image.create(c)
                        .drawableRes(R.drawable.save)
                        .withLayout()
                        .alignSelf(YogaAlign.CENTER)
                        .widthDip(20)
                        .heightDip(20)
                        .marginDip(END, CARD_INTERNAL_PADDING))
                .child(Text.create(c, 0, R.style.save_text).text("Save"))
                .border(Border.create(c).color(ALL, Color.BLACK).widthDip(TOP, 1).build()))
        .border(Border.create(c).color(ALL, Color.BLACK).widthDip(ALL, 1).build())
        .build();
  }
}
